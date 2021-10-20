package com.velog.service.board;

import com.velog.domain.board.Board;
import com.velog.domain.board.BoardHashTag;
import com.velog.domain.board.BoardLike;
import com.velog.domain.board.repository.BoardHashTagRepository;
import com.velog.domain.board.repository.BoardLikeRepository;
import com.velog.dto.board.response.BoardRetrieveResponse;
import com.velog.enumData.BoardPeriod;
import com.velog.domain.board.Series;
import com.velog.domain.board.repository.BoardRepository;
import com.velog.domain.board.repository.SeriesRepository;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.domain.testObject.BoardCreator;
import com.velog.domain.testObject.MemberCreator;
import com.velog.dto.board.request.BoardRequest;
import com.velog.exception.NotFoundException;
import com.velog.exception.ValidationException;
import com.velog.service.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardHashTagRepository boardHashTagRepository;

    @Autowired
    private BoardLikeRepository boardLikeRepository;

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
        seriesRepository.deleteAll();
        boardRepository.deleteAll();
        boardHashTagRepository.deleteAll();
        boardLikeRepository.deleteAll();
    }

    @Test
    void 멤버가_시리즈를_생성한다() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);

        BoardRequest.CreateSeries request = new BoardRequest.CreateSeries("자바");

        // when
        boardService.createSeries(request, member.getEmail().getEmail());

        // then
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(1);
    }

    @Test
    void 게시글을_생성한다() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);

        BoardRequest.CreateBoard request = BoardRequest.CreateBoard.testBuilder()
                .title("title")
                .content("content")
                .boardThumbnailUrl("https://naver.com")
                .isPrivate(true)
                .seriesId(1L)
                .hashTagList(Collections.emptyList())
                .build();

        // when
        boardService.createBoard(request, member.getId());

        // then
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList).hasSize(1);
        TestUtils.assertBoard(boardList.get(0), request.getContent(), request.getTitle(), request.getBoardThumbnailUrl(), request.getIsPrivate(), request.getSeriesId());
        List<BoardHashTag> boardHashTagList = boardHashTagRepository.findAll();
        assertThat(boardHashTagList).isEmpty();
    }

    @Test
    void 게시글을_생성한다_해쉬태그가_있을_경우() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);

        BoardRequest.CreateBoard request = BoardRequest.CreateBoard.testBuilder()
                .title("title")
                .content("content")
                .boardThumbnailUrl("https://naver.com")
                .isPrivate(true)
                .seriesId(1L)
                .hashTagList(Arrays.asList("자바", "스트림"))
                .build();

        // when
        boardService.createBoard(request, member.getId());

        // then
        List<Board> boardList = boardRepository.findAll();
        List<BoardHashTag> boardHashTagList = boardHashTagRepository.findAll();
        assertThat(boardList).hasSize(1);
        TestUtils.assertBoard(boardList.get(0), request.getContent(), request.getTitle(), request.getBoardThumbnailUrl(), request.getIsPrivate(), request.getSeriesId());

        assertThat(boardHashTagList).hasSize(2);
        assertThat(boardHashTagList.get(0).getHashTag()).isEqualTo(request.getHashTagList().get(0));
        assertThat(boardHashTagList.get(1).getHashTag()).isEqualTo(request.getHashTagList().get(1));
    }

    @DisplayName("최신 게시글을 불러온다. lastBoardId 5 size 2  게시글을 desc해서 마지막 게시글이 5이고 사이즈가 2이면 4,3 이 있다.")
    @Test
    void 최신_게시글을_불러온다1() {
        Member member = MemberCreator.create();
        memberRepository.save(member);
        // given
        Board board1 = BoardCreator.create("title1", member.getId());
        Board board2 = BoardCreator.create("title2", member.getId());
        Board board3 = BoardCreator.create("title3", member.getId());
        Board board4 = BoardCreator.create("title4", member.getId());
        Board board5 = BoardCreator.create("title5", member.getId());

        boardRepository.saveAll(Arrays.asList(board1, board2, board3, board4, board5));

        // when
        List<BoardRetrieveResponse> boardList = boardService.retrieveBoard(board5.getId(), 2, BoardPeriod.LATEST, null);

        // then
        assertThat(boardList).hasSize(2);
        assertThat(boardList.get(0).getTitle()).isEqualTo(board4.getTitle());
        assertThat(boardList.get(1).getTitle()).isEqualTo(board3.getTitle());
    }

    @DisplayName("최신 게시글을 불러온다. lastBoardId 2 size 2 게시글을 desc해서 마지막 게시글이 2이고 사이즈가 2인데 게시글이 하나남아서 1번 게시글만 있다")
    @Test
    void 최신_게시글을_불러온다2() {
        Member member = MemberCreator.create();
        memberRepository.save(member);
        // given
        Board board1 = BoardCreator.create("title1", member.getId());
        Board board2 = BoardCreator.create("title2", member.getId());
        Board board3 = BoardCreator.create("title3", member.getId());
        Board board4 = BoardCreator.create("title4", member.getId());
        Board board5 = BoardCreator.create("title5", member.getId());

        boardRepository.saveAll(Arrays.asList(board1, board2, board3, board4, board5));

        // when
        List<BoardRetrieveResponse> boardList = boardService.retrieveBoard(board2.getId(), 2, BoardPeriod.LATEST, null);

        // then
        assertThat(boardList).hasSize(1);
        assertThat(boardList.get(0).getTitle()).isEqualTo(board1.getTitle());
    }

    @Test
    void 게시물이_없을경우_빈배열_반환한다() {
        // when
        List<BoardRetrieveResponse> boardList = boardService.retrieveBoard(0L, 2, BoardPeriod.LATEST, null);

        // then
        assertThat(boardList).isEmpty();
    }

    // TODO: 2021-08-15 createdDate 기준으로 오늘, 이번주, 이번달, 이번년도 게시글 가지고 오기 - test짜야함

    @Test
    void 게시물을_좋아요한다() {
        // given
        Board board = BoardCreator.create("title1", 1L);
        boardRepository.save(board);

        BoardRequest.GetBoardRequest request = BoardRequest.GetBoardRequest.testInstance(board.getId());

        // when
        boardService.boardLike(board.getId(), 1L);

        // then
        List<BoardLike> boardLikeList = boardLikeRepository.findAll();
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardLikeList).hasSize(1);
        assertThat(boardList.get(0).getLikeCount()).isEqualTo(1);
    }

    @Test
    void 좋아요한_게시물이_존재하지_않는_게시물일_경우_예외발생() {
        // when & then
        assertThatThrownBy(
            () -> boardService.boardLike(1L, 1L)
        ).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 좋아요한_게시물이_이미_좋아요한_게시물일_경우_예외발생() {
        // given
        Board board = BoardCreator.create("title1", 1L);
        boardRepository.save(board);

        BoardLike boardLike = BoardLike.of(board, 1L);
        boardLikeRepository.save(boardLike);

        assertThatThrownBy(
            () -> boardService.boardLike(board.getId(), 1L)
        ).isInstanceOf(ValidationException.class);
    }

    @Test
    void 게시물_좋아요_취소한다() {
        // given
        Board board = BoardCreator.create("title1", 1L);
        boardRepository.save(board);

        BoardLike boardLike = BoardLike.of(board, 1L);
        boardLikeRepository.save(boardLike);

        // when
        boardService.boardUnLike(board.getId(), 1L);

        // then
        List<BoardLike> boardLikeList = boardLikeRepository.findAll();
        assertThat(boardLikeList).isEmpty();
    }

    @Test
    void 좋아요를_하지않은_게시물을_취소할_경우_예외발생() {
        // when & then
        assertThatThrownBy(
            () -> boardService.boardUnLike(1L, 1L)
        ).isInstanceOf(NotFoundException.class);
    }

}
