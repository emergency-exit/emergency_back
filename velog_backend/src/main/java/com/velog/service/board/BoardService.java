package com.velog.service.board;

import com.velog.domain.board.Board;
import com.velog.domain.board.repository.BoardRepository;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.dto.board.response.BoardInfoWithHashTagResponse;
import com.velog.dto.board.response.BoardRetrieveResponse;
import com.velog.enumData.BoardPeriod;
import com.velog.dto.board.request.BoardRequest;
import com.velog.dto.board.response.BoardInfoResponse;
import com.velog.dto.board.response.SeriesResponse;
import com.velog.exception.NotFoundException;
import com.velog.service.member.MemberServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createSeries(BoardRequest.CreateSeries request, String email) {
        Member member = MemberServiceUtils.findMemberByEmail(memberRepository, email);
        member.addSeries(request.getSeriesName());
    }

    @Transactional
    public BoardInfoResponse createBoard(BoardRequest.CreateBoard request, Long memberId) {
        Board board = boardRepository.save(request.toEntity(memberId));
        board.addHashTag(request.getHashTagList(), memberId);
        return BoardInfoResponse.of(board);
    }

    @Transactional(readOnly = true)
    public List<BoardRetrieveResponse> retrieveBoard(Long lastBoardId, int size, BoardPeriod period, Long memberId) {
        return boardRepository.findAllBoardByOrderByIdDescAndTerm(lastBoardId, size, period, memberId);
    }

    @Transactional
    public void boardLike(Long boardId, Long memberId) {
        Board board = boardRepository.findBoardById(boardId)
                .orElseThrow(() -> new NotFoundException(String.format("존재하지 않는 게시글 %s입니다.", boardId)));
        board.boardAddLike(memberId);
    }

    @Transactional
    public void boardUnLike(Long boardId, Long memberId) {
        Board board = boardRepository.findBoardById(boardId)
                .orElseThrow(() -> new NotFoundException(String.format("존재하지 않는 게시글 %s입니다.", boardId)));
        board.boardUnLike(memberId);
    }

    @Transactional
    public BoardInfoWithHashTagResponse getBoard(Long boardId) {
        boardRepository.findBoardById(boardId)
                .orElseThrow(() -> new NotFoundException(String.format("존재하지 않는 게시글 %s입니다.", boardId)));
        Board boardWithHashTag = boardRepository.getBoardWithHashTag(boardId);
        Member member = memberRepository.findMemberById(boardWithHashTag.getMemberId())
                .orElseThrow(() -> new NotFoundException(String.format("%s는 존재하지 않는 유저입니다.", boardWithHashTag.getMemberId())));
        return BoardInfoWithHashTagResponse.of(boardWithHashTag, member);
    }

    @Transactional
    public BoardInfoWithHashTagResponse getMyBoard(Long boardId, Long memberId) {
        Board boardWithHashTag = boardRepository.findBoardWithHashTagByIdAndMemberId(boardId, memberId)
                .orElseThrow(() -> new NotFoundException(String.format("해당 멤버 (%s)의 게시글 (%s)는 존재하지 않습니다", memberId, boardId)));
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new NotFoundException(String.format("%s는 존재하지 않는 유저입니다.", memberId)));
        return BoardInfoWithHashTagResponse.of(boardWithHashTag, member);
    }

    @Transactional(readOnly = true)
    public List<SeriesResponse> retrieveSeries(Long memberId) {
        Member member = memberRepository.findSeriesByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(String.format("존재하지 않는 멤버 %s 입니다.", memberId)));
        return member.getSeriesList().stream().map(SeriesResponse::of).collect(Collectors.toList());
    }

}
