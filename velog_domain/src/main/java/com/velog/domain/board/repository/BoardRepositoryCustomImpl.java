package com.velog.domain.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.velog.domain.board.Board;
import com.velog.enumData.BoardPeriod;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.velog.domain.board.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> findAllBoardByOrderByIdDescAndTerm(Long lastBoardId, int size, BoardPeriod period) {
        return queryFactory.selectFrom(board)
                .where(
                        paginationByLastBoardId(lastBoardId),
                        devideByPeriod(period)
                )
                .orderBy(board.id.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public Optional<Board> findBoardById(Long boardId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(board)
                .where(board.id.eq(boardId))
                .fetchOne()
        );
    }

    private BooleanExpression paginationByLastBoardId(Long lastBoardId) {
        if (lastBoardId == 0) {
            return null;
        }
        return board.id.lt(lastBoardId);
    }

    private BooleanExpression devideByPeriod(BoardPeriod period) {
        LocalDate now = LocalDate.now();
        if (period == BoardPeriod.TODAY) {
            return board.createdDate.before(LocalDateTime.of(now.plusDays(1), LocalTime.MIN))
                    .and(board.createdDate.after(LocalDateTime.of(now, LocalTime.MIN)));
        }
        if (period == BoardPeriod.THIS_MONTH) {
            return board.createdDate.before(LocalDateTime.of(now.plusDays(1), LocalTime.MIN))
                    .and(board.createdDate.after(LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0)));
        }
        if (period == BoardPeriod.THIS_WEEK) {
            return board.createdDate.before(LocalDateTime.of(now.plusDays(1), LocalTime.MIN))
                    .and(board.createdDate.after(LocalDateTime.of(now.minusDays(7), LocalTime.MIN)));
        }
        if (period == BoardPeriod.THIS_YEAR) {
            return board.createdDate.before(LocalDateTime.of(now.plusDays(1), LocalTime.MIN))
                    .and(board.createdDate.after(LocalDateTime.of(now.getYear(), 1, 1, 0, 0)));
        }
        return null;
    }

}
