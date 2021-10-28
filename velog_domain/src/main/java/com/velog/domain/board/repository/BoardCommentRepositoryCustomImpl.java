package com.velog.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.velog.domain.board.BoardComment;
import com.velog.domain.board.QBoardComment;
import lombok.RequiredArgsConstructor;

import java.util.List;
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

    @Override
    public List<BoardComment> findBoardCommentByBoardId(Long boardId) {
        return queryFactory.selectFrom(boardComment)
                .leftJoin(boardComment.childComments, new QBoardComment("child")).fetchJoin()
                .where(
                        boardComment.deleteDate.isNull(),
                        boardComment.boardId.eq(boardId),
                        boardComment.parentComment.isNull()
                )
                .fetch();
    }

}
