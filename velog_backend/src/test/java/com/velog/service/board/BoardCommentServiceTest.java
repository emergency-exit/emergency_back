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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void 게시글에_댓글을_작성한다() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);
        Board board = BoardCreator.create("title", member.getId());
        boardRepository.save(board);

        BoardCommentRequest.CreateBoardComment request = BoardCommentRequest.CreateBoardComment.testInstance(board.getId(), "와 댓글이다~");

        // when
        boardCommentService.createBoardComment(request, member);

        // then
        final List<BoardComment> boardCommentList = boardCommentRepository.findAll();
        assertThat(boardCommentList).hasSize(1);
        assertThat(boardCommentList.get(0).getContent()).isEqualTo(request.getContent());
        assertThat(boardCommentList.get(0).getBoardId()).isEqualTo(board.getId());
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

}
