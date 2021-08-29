package com.velog.domain.board.repository;

import com.velog.domain.board.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
}
