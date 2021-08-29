package com.velog.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.velog.domain.board.BoardComment;
import com.velog.domain.board.QBoardComment;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.velog.domain.board.QBoardComment.boardComment;

@RequiredArgsConstructor
public class BoardCommentRepositoryCustomImpl implements BoardCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<BoardComment> findBoardCommentById(Long boardCommentId) {
        return Optional.ofNullable(queryFactory.selectFrom(boardComment)
                .where(
                        boardComment.id.eq(boardCommentId),
                        boardComment.deleteDate.isNull()
                ).fetchOne()
        );
    }
}
