package com.velog.dto.board.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeriesResponse {

    private Long seriesId;
    private String seriesName;

    public SeriesResponse(Long seriesId, String seriesName) {
        this.seriesId = seriesId;
        this.seriesName = seriesName;
    }

    public static SeriesResponse of(Long seriesId, String seriesName) {
        return new SeriesResponse(seriesId, seriesName);
    }

}
