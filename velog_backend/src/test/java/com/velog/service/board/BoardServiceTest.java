package com.velog.service.board;

import com.velog.domain.board.Board;
import com.velog.domain.board.BoardHashTag;
import com.velog.domain.board.BoardLike;
import com.velog.domain.board.repository.BoardHashTagRepository;
import com.velog.domain.board.repository.BoardLikeRepository;
import com.velog.dto.board.response.BoardInfoWithHashTagResponse;
import com.velog.dto.board.response.BoardRetrieveResponse;
import com.velog.dto.board.response.SeriesResponse;
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
    void ?????????_????????????_????????????() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);

        BoardRequest.CreateSeries request = new BoardRequest.CreateSeries("??????");

        // when
        boardService.createSeries(request, member.getEmail().getEmail());

        // then
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(1);
        assertThat(seriesList.get(0).getSeriesName()).isEqualTo(request.getSeriesName());
    }

    @Test
    void ??????_??????_?????????_????????????_????????????() {
        // given
        Member member = MemberCreator.create();
        member.addSeries("??????");
        memberRepository.save(member);

        BoardRequest.CreateSeries request = new BoardRequest.CreateSeries("??????");

        // when & then
        assertThatThrownBy(
            () -> boardService.createSeries(request, member.getEmail().getEmail())
        ).isInstanceOf(ValidationException.class);
    }

    @Test
    void ??????_?????????_???????????????_?????????_?????????_?????????_??????_?????????() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);

        Member member2 = MemberCreator.create("test@test.com", "test123$");
        member2.addSeries("??????");
        memberRepository.save(member2);

        BoardRequest.CreateSeries request = new BoardRequest.CreateSeries("??????");

        // when
        boardService.createSeries(request, member.getEmail().getEmail());

        // then
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(2);
        assertThat(seriesList.get(0).getSeriesName()).isEqualTo(request.getSeriesName());
        assertThat(seriesList.get(1).getSeriesName()).isEqualTo(request.getSeriesName());
    }

    @Test
    void ?????????_????????????_????????????() {
        // given
        Member member = MemberCreator.create();
        member.addSeries("??????");
        member.addSeries("?????????");
        memberRepository.save(member);

        // when
        List<SeriesResponse> seriesResponses = boardService.retrieveSeries(member.getId());

        // then
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(2);
        assertThat(seriesResponses).hasSize(2);
        assertThat(seriesResponses.get(0).getSeriesName()).isEqualTo("??????");
        assertThat(seriesResponses.get(1).getSeriesName()).isEqualTo("?????????");
    }

    @Test
    void ?????????_????????????_?????????_????????????_????????????() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);

        // when
        List<SeriesResponse> seriesResponses = boardService.retrieveSeries(member.getId());

        // then
        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).isEmpty();
        assertThat(seriesResponses).isEmpty();
    }

    @Test
    void ????????????_????????????() {
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
    void ????????????_????????????_???????????????_??????_??????() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);

        BoardRequest.CreateBoard request = BoardRequest.CreateBoard.testBuilder()
                .title("title")
                .content("content")
                .boardThumbnailUrl("https://naver.com")
                .isPrivate(true)
                .seriesId(1L)
                .hashTagList(Arrays.asList("??????", "?????????"))
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

    @DisplayName("?????? ???????????? ????????????. lastBoardId 5 size 2  ???????????? desc?????? ????????? ???????????? 5?????? ???????????? 2?????? 4,3 ??? ??????.")
    @Test
    void ??????_????????????_????????????1() {
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

    @DisplayName("?????? ???????????? ????????????. lastBoardId 2 size 2 ???????????? desc?????? ????????? ???????????? 2?????? ???????????? 2?????? ???????????? ??????????????? 1??? ???????????? ??????")
    @Test
    void ??????_????????????_????????????2() {
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
    void ????????????_????????????_?????????_????????????() {
        // when
        List<BoardRetrieveResponse> boardList = boardService.retrieveBoard(0L, 2, BoardPeriod.LATEST, null);

        // then
        assertThat(boardList).isEmpty();
    }

    // TODO: 2021-08-15 createdDate ???????????? ??????, ?????????, ?????????, ???????????? ????????? ????????? ?????? - test?????????

    @Test
    void ??????_???_?????????_????????????_????????????() {
        // given
        Member member = MemberCreator.create();
        memberRepository.save(member);

        Board board1 = BoardCreator.create("title1", member.getId());
        board1.addHashTag(Arrays.asList("??????", "?????????"), member.getId());
        boardRepository.save(board1);

        // when
        BoardInfoWithHashTagResponse response = boardService.getMyBoard(board1.getId(), member.getId());

        // then
        assertThat(response.getHashTagList()).hasSize(2);
        assertThat(response.getBoardInfoResponse().getTitle()).isEqualTo(board1.getTitle());
        assertThat(response.getMemberInfoResponse().getEmail()).isEqualTo(member.getEmail().getEmail());
    }

    @Test
    void ????????????_???????????????() {
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
    void ????????????_????????????_????????????_??????_????????????_??????_????????????() {
        // when & then
        assertThatThrownBy(
            () -> boardService.boardLike(1L, 1L)
        ).isInstanceOf(NotFoundException.class);
    }

    @Test
    void ????????????_????????????_??????_????????????_????????????_??????_????????????() {
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
    void ?????????_?????????_????????????() {
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
    void ????????????_????????????_????????????_?????????_??????_????????????() {
        // when & then
        assertThatThrownBy(
            () -> boardService.boardUnLike(1L, 1L)
        ).isInstanceOf(NotFoundException.class);
    }

}
