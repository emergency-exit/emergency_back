package com.velog.service.board;

import com.velog.domain.board.Board;
import com.velog.domain.board.BoardPeriod;
import com.velog.domain.board.Series;
import com.velog.domain.board.repository.BoardRepository;
import com.velog.domain.board.repository.SeriesRepository;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.domain.testObject.BoardCreator;
import com.velog.domain.testObject.MemberCreator;
import com.velog.dto.board.request.BoardRequest;
import com.velog.service.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
        seriesRepository.deleteAll();
        boardRepository.deleteAll();
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
                .memberId(member.getId())
                .seriesId(1L)
                .build();

        // when
        boardService.createBoard(request, member.getId());

        // then
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList).hasSize(1);
        TestUtils.assertBoard(boardList.get(0), request.getContent(), request.getTitle(), request.getBoardThumbnailUrl(), request.getIsPrivate(), request.getSeriesId());
    }

    @DisplayName("최신 게시글을 불러온다. lastBoardId 5 size 2  게시글을 desc해서 마지막 게시글이 5이고 사이즈가 2이면 4,3 이 있다.")
    @Test
    void 최신_게시글을_불러온다1() {
        // given
        Board board1 = BoardCreator.create("title1");
        Board board2 = BoardCreator.create("title2");
        Board board3 = BoardCreator.create("title3");
        Board board4 = BoardCreator.create("title4");
        Board board5 = BoardCreator.create("title5");

        boardRepository.saveAll(Arrays.asList(board1, board2, board3, board4, board5));

        // when
        List<Board> boardList = boardService.retrieveBoard(5L, 2, BoardPeriod.LATEST);

        // then
        assertThat(boardList).hasSize(2);
        assertThat(boardList.get(0).getTitle()).isEqualTo(board4.getTitle());
        assertThat(boardList.get(0).getTitle()).isEqualTo(board3.getTitle());
    }

    @DisplayName("최신 게시글을 불러온다. lastBoardId 2 size 2  게시글을 desc해서 마지막 게시글이 2이고 사이즈가 2인데 게시글이 하나남아서 1번 게시글만 있다")
    @Test
    void 최신_게시글을_불러온다2() {
        // given
        Board board1 = BoardCreator.create("title1");
        Board board2 = BoardCreator.create("title2");
        Board board3 = BoardCreator.create("title3");
        Board board4 = BoardCreator.create("title4");
        Board board5 = BoardCreator.create("title5");

        boardRepository.saveAll(Arrays.asList(board1, board2, board3, board4, board5));

        // when
        List<Board> boardList = boardService.retrieveBoard(2L, 2, BoardPeriod.LATEST);

        // then
        assertThat(boardList).hasSize(1);
        assertThat(boardList.get(0).getTitle()).isEqualTo(board1.getTitle());
    }

    @DisplayName("최신 게시글을 불러온다. lastBoardId 2 size 2  게시글을 desc해서 마지막 게시글이 2이고 사이즈가 2인데 게시글이 하나남아서 1번 게시글만 있다")
    @Test
    void 오늘_게시글을_불러온다1() {
        // given
        Board board1 = BoardCreator.create("title1");
        Board board2 = BoardCreator.create("title2");
        Board board3 = BoardCreator.create("title3");
        Board board4 = BoardCreator.create("title4");
        Board board5 = BoardCreator.create("title5");

        boardRepository.saveAll(Arrays.asList(board1, board2, board3, board4, board5));

        // when
        List<Board> boardList = boardService.retrieveBoard(2L, 2, BoardPeriod.LATEST);

        // then
        assertThat(boardList).hasSize(1);
        assertThat(boardList.get(0).getTitle()).isEqualTo(board1.getTitle());
    }

}
