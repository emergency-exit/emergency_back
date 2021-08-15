package com.velog.dto.board.response;

import com.velog.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardInfoResponse {

    private Long boardId;

    private Long seriesId;

    private String title;

    private String content;

    private Boolean isPrivate;

    private int likeCount;

    private String boardThumbnailUrl;

    @Builder
    public BoardInfoResponse(Long boardId, Long seriesId, String title, String content, Boolean isPrivate, int likeCount, String boardThumbnailUrl) {
        this.boardId = boardId;
        this.seriesId = seriesId;
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
        this.likeCount = likeCount;
        this.boardThumbnailUrl = boardThumbnailUrl;
    }

    public static BoardInfoResponse of(Board board) {
        return BoardInfoResponse.builder()
                .boardId(board.getId())
                .seriesId(board.getSeriesId())
                .title(board.getTitle())
                .content(board.getContent())
                .isPrivate(board.getIsPrivate())
                .likeCount(board.getLikeCount())
                .boardThumbnailUrl(board.getBoardThumbnailUrl())
                .build();
    }

}
