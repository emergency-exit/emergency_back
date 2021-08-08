package com.velog.dto.board.request;

import com.velog.domain.board.Board;
import com.velog.enumData.BoardPeriod;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class BoardRequest {

    @Getter
    @NoArgsConstructor
    public static class CreateSeries {

        private String seriesName;

        public CreateSeries(String seriesName) {
            this.seriesName = seriesName;
        }

    }

    @Getter
    @NoArgsConstructor
    public static class CreateBoard {

        private Long seriesId;

        @NotBlank
        private String title;

        private String content;

        @NotNull
        private Long memberId;

        @NotNull
        private Boolean isPrivate;

        private String boardThumbnailUrl;

        @Builder(builderMethodName = "testBuilder")
        public CreateBoard(Long seriesId, String title, String content, Long memberId, Boolean isPrivate, String boardThumbnailUrl) {
            this.seriesId = seriesId;
            this.title = title;
            this.content = content;
            this.memberId = memberId;
            this.isPrivate = isPrivate;
            this.boardThumbnailUrl = boardThumbnailUrl;
        }

        public Board toEntity(Long memberId) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .seriesId(seriesId)
                    .memberId(memberId)
                    .isPrivate(isPrivate)
                    .boardThumbnailUrl(boardThumbnailUrl)
                    .build();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RetrieveBoardRequest {

        private Long lastBoardId;
        private int size;
        private BoardPeriod period;

    }

}
