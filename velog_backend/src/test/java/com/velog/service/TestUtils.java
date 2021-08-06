package com.velog.service;

import com.velog.domain.board.Board;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

    public static void assertBoard(Board board, String content, String title, String boardThumbnailUrl, Boolean isPrivate, Long seriesId) {
        assertThat(board.getContent()).isEqualTo(content);
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getBoardThumbnailUrl()).isEqualTo(boardThumbnailUrl);
        assertThat(board.getIsPrivate()).isEqualTo(isPrivate);
        assertThat(board.getSeriesId()).isEqualTo(seriesId);
    }

}
