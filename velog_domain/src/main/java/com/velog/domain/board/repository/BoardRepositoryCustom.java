package com.velog.domain.board.repository;

import com.velog.domain.board.Board;
import com.velog.enumData.BoardPeriod;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {

    List<Board> findAllBoardByOrderByIdDescAndTerm(Long lastBoardId, int size, BoardPeriod period);

    Optional<Board> findBoardById(Long boardId);

}
