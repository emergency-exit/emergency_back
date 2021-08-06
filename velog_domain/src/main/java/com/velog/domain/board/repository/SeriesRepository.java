package com.velog.domain.board.repository;

import com.velog.domain.board.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
