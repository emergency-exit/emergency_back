package com.velog.domain.board.repository;

import com.velog.domain.board.BoardComment;

import java.util.List;
import java.util.Optional;

public interface BoardCommentRepositoryCustom {

    Optional<BoardComment> findBoardCommentById(Long boardCommentId);

    List<BoardComment> findBoardCommentByBoardId(Long boardId);

}
