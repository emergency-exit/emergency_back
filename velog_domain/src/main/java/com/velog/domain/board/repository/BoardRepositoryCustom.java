package com.velog.domain.board.repository;

import com.velog.domain.board.Board;
import com.velog.dto.board.response.BoardRetrieveResponse;
import com.velog.enumData.BoardPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {

    List<BoardRetrieveResponse> findAllBoardByOrderByIdDescAndTerm(Long lastBoardId, int size, BoardPeriod period, Long memberId);

    Optional<Board> findBoardById(Long boardId);

    Optional<Board> getBoardWithHashTag(Long boardId);

    Optional<Board> findBoardWithHashTagByIdAndMemberId(Long boardId, Long memberId);

    Page<Board> findBySearchingPagination(Pageable pageable, String search);

}
