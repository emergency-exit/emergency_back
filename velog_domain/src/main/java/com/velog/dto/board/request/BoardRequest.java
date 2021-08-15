package com.velog.dto.board.request;

import com.velog.domain.board.Board;
import com.velog.enumData.BoardPeriod;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

        @NotBlank(message = "title을 넣어주세요")
        private String title;

        private String content;

        @NotNull
        private Long memberId;

        @NotNull(message = "공개 유무를 넣어주세요")
        private Boolean isPrivate;

        private String boardThumbnailUrl;

        @NotNull
        private List<String> hashTagList = new ArrayList<>();

        @Builder(builderMethodName = "testBuilder")
        public CreateBoard(Long seriesId, String title, String content, Long memberId, Boolean isPrivate, String boardThumbnailUrl, List<String> hashTagList) {
            this.seriesId = seriesId;
            this.title = title;
            this.content = content;
            this.memberId = memberId;
            this.isPrivate = isPrivate;
            this.boardThumbnailUrl = boardThumbnailUrl;
            this.hashTagList = hashTagList;
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

    @Getter
    @NoArgsConstructor
    public static class GetBoardRequest {

        @NotNull
        private Long boardId;

        public GetBoardRequest(Long boardId) {
            this.boardId = boardId;
        }

        public static GetBoardRequest testInstance(Long boardId) {
            return new GetBoardRequest(boardId);
        }

    }

}
