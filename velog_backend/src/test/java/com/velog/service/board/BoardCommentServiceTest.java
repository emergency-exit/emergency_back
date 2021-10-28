package com.velog.service.board;

import com.velog.domain.board.Board;
import com.velog.domain.board.BoardComment;
import com.velog.domain.board.repository.BoardCommentRepository;
import com.velog.domain.board.repository.BoardRepository;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.domain.testObject.BoardCommentCreator;
import com.velog.domain.testObject.BoardCreator;
import com.velog.domain.testObject.MemberCreator;
import com.velog.dto.boardComment.request.BoardCommentRequest;
import com.velog.dto.boardComment.response.BoardCommentInfoResponse;
import com.velog.exception.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BoardCommentServiceTest {

    @Autowired
    private BoardCommentService boardCommentService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardCommentRepository boardCommentRepository;

    @AfterEach
    void clean() {
        boardRepository.deleteAll();
        memberRepository.deleteAll();
        boardCommentRepository.deleteAll();
    }

    @Test
    void 게시글에_댓글을_작성한다_부모_댓글이_없을_경우() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);
        Board board = BoardCreator.create("title", member.getId());
        boardRepository.save(board);

        BoardCommentRequest.CreateBoardComment request = BoardCommentRequest.CreateBoardComment.rootTestInstance(board.getId(), "와 댓글이다~");

        // when
        boardCommentService.createBoardComment(request, member);

        // then
        final List<BoardComment> boardCommentList = boardCommentRepository.findAll();
        assertThat(boardCommentList).hasSize(1);
        assertThat(boardCommentList.get(0).getContent()).isEqualTo(request.getContent());
        assertThat(boardCommentList.get(0).getBoardId()).isEqualTo(board.getId());
        assertThat(boardCommentList.get(0).getParentComment()).isNull();
    }

    @Test
    void 게시글에_댓글을_작성한다_부모_댓글이_있을_경우() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);
        Board board = BoardCreator.create("title", member.getId());
        boardRepository.save(board);

        BoardComment boardComment = BoardComment.newRootComment(board.getId(), "부모댓글입니다.", member.getId());
        boardCommentRepository.save(boardComment);

        BoardCommentRequest.CreateBoardComment request = BoardCommentRequest.CreateBoardComment.childTestInstance(board.getId(), "와 자식 댓글이다", boardComment.getId());

        // when
        boardCommentService.createBoardComment(request, member);

        // then
        final List<BoardComment> boardCommentList = boardCommentRepository.findAll();
        assertThat(boardCommentList).hasSize(2);
        assertThat(boardCommentList.get(1).getContent()).isEqualTo(request.getContent());
        assertThat(boardCommentList.get(1).getBoardId()).isEqualTo(board.getId());
        assertThat(boardCommentList.get(1).getDepth()).isEqualTo(1);
    }

    @Test
    void 게시글에_댓글을_작성한다_뎁스가_1일경우() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);
        Board board = BoardCreator.create("title", member.getId());
        boardRepository.save(board);

        BoardComment boardComment = BoardComment.newRootComment(board.getId(), "부모댓글입니다.", member.getId());
        boardComment.addChildComment(member.getId(), "자식댓글입니다.", board.getId());
        boardCommentRepository.save(boardComment);

        BoardCommentRequest.CreateBoardComment request = BoardCommentRequest.CreateBoardComment.childTestInstance(board.getId(), "와 댓글이다~", boardComment.getChildComments().get(0).getId());

        assertThatThrownBy(
                () -> boardCommentService.createBoardComment(request, member)
        ).isInstanceOf(ValidationException.class);
    }

    @Test
    void 게시글_댓글을_수정한다() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);
        Board board = BoardCreator.create("title", member.getId());
        boardRepository.save(board);
        final BoardComment boardComment = BoardCommentCreator.create(board.getId(), member.getId(), "댓글이다~");
        boardCommentRepository.save(boardComment);

        final BoardCommentRequest.UpdateBoardComment request = BoardCommentRequest.UpdateBoardComment.testInstance(boardComment.getId(), "댓글 수정");

        // when
        boardCommentService.updateBoardComment(request, member);

        // then
        final List<BoardComment> boardCommentList = boardCommentRepository.findAll();
        assertThat(boardCommentList).hasSize(1);
        assertThat(boardCommentList.get(0).getContent()).isEqualTo(request.getContent());
        assertThat(boardCommentList.get(0).getBoardId()).isEqualTo(board.getId());
    }

    @Test
    void 게시글_댓글중에_자식댓글을_수정한다() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);
        Board board = BoardCreator.create("title", member.getId());
        boardRepository.save(board);
        final BoardComment boardComment = BoardCommentCreator.create(board.getId(), member.getId(), "부모 댓글이다~");
        boardComment.addChildComment(member.getId(), "자식댓글이다.", board.getId());
        boardCommentRepository.save(boardComment);

        final BoardCommentRequest.UpdateBoardComment request = BoardCommentRequest.UpdateBoardComment.testInstance(boardComment.getChildComments().get(0).getId(), "댓글 수정");

        // when
        boardCommentService.updateBoardComment(request, member);

        // then
        final List<BoardComment> boardCommentList = boardCommentRepository.findAll();
        assertThat(boardCommentList).hasSize(2);
        assertThat(boardCommentList.get(1).getContent()).isEqualTo(request.getContent());
        assertThat(boardCommentList.get(1).getBoardId()).isEqualTo(board.getId());
    }

    @Test
    void 게시글의_댓글들을_불러온다() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);
        Board board = BoardCreator.create("title", member.getId());
        boardRepository.save(board);
        BoardComment boardComment1 = BoardCommentCreator.create(board.getId(), member.getId(), "댓글이다1~");
        BoardComment boardComment2 = BoardCommentCreator.create(board.getId(), member.getId(), "댓글이다2~");
        BoardComment boardComment3 = BoardCommentCreator.create(board.getId(), member.getId(), "댓글이다3~");
        boardCommentRepository.saveAll(Arrays.asList(boardComment1, boardComment2, boardComment3));

        // when
        List<BoardCommentInfoResponse> boardCommentInfoResponses = boardCommentService.retrieveBoardComment(board.getId());

        // then
        assertThat(boardCommentInfoResponses).hasSize(3);
        assertThat(boardCommentInfoResponses.get(0).getContent()).isEqualTo(boardComment1.getContent());
        assertThat(boardCommentInfoResponses.get(0).getMemberInfoResponse().getEmail()).isEqualTo(member.getEmail().getEmail());
    }

    @Test
    void 게시글의_댓글들을_불러올_때_자식댓글도_불러온다() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);
        Board board = BoardCreator.create("title", member.getId());
        boardRepository.save(board);
        BoardComment boardComment1 = BoardCommentCreator.create(board.getId(), member.getId(), "부모댓글이다1~");
        boardComment1.addChildComment(member.getId(), "부모 1의 자식댓글1이다", board.getId());
        boardComment1.addChildComment(member.getId(), "부모 1의 자식댓글2이다", board.getId());
        BoardComment boardComment2 = BoardCommentCreator.create(board.getId(), member.getId(), "부모댓글이다2~");
        boardComment2.addChildComment(member.getId(), "부모2의 자식댓글1이다.", board.getId());
        BoardComment boardComment3 = BoardCommentCreator.create(board.getId(), member.getId(), "부모댓글이다3~");
        boardCommentRepository.saveAll(Arrays.asList(boardComment1, boardComment2, boardComment3));

        // when
        List<BoardCommentInfoResponse> boardCommentInfoResponseList = boardCommentService.retrieveBoardComment(board.getId());

        // then
        List<BoardComment> boardCommentList = boardCommentRepository.findAll();
        assertThat(boardCommentList).hasSize(6);
        assertThat(boardCommentInfoResponseList.get(0).getContent()).isEqualTo(boardComment1.getContent());
        assertThat(boardCommentInfoResponseList.get(0).getMemberInfoResponse().getEmail()).isEqualTo(member.getEmail().getEmail());
        assertThat(boardCommentInfoResponseList.get(0).getChildCommentList().get(0).getContent()).isEqualTo(boardComment1.getChildComments().get(0).getContent());
        assertThat(boardCommentInfoResponseList.get(0).getChildCommentList().get(1).getContent()).isEqualTo(boardComment1.getChildComments().get(1).getContent());
    }

    @Test
    void 게시글의_댓글들을_불러오는데_유저가_삭제된_유저일_경우_유저정보는_널이_나온다() {
        // given
        Board board = BoardCreator.create("title", 1L);
        boardRepository.save(board);
        BoardComment boardComment1 = BoardCommentCreator.create(board.getId(), 1L, "댓글이다1~");
        BoardComment boardComment2 = BoardCommentCreator.create(board.getId(), 1L, "댓글이다2~");
        BoardComment boardComment3 = BoardCommentCreator.create(board.getId(), 1L, "댓글이다3~");
        boardCommentRepository.saveAll(Arrays.asList(boardComment1, boardComment2, boardComment3));

        // when
        List<BoardCommentInfoResponse> boardCommentInfoResponses = boardCommentService.retrieveBoardComment(board.getId());

        // then
        assertThat(boardCommentInfoResponses).hasSize(3);
        assertThat(boardCommentInfoResponses.get(0).getMemberInfoResponse().getName()).isNull();
        assertThat(boardCommentInfoResponses.get(0).getMemberInfoResponse().getMemberImage()).isNull();
    }

}
