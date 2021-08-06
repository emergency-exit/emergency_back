package com.velog.dto.board.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
