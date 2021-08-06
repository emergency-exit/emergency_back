package com.velog.dto.board.response;

import com.velog.domain.board.Series;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeriesResponse {

    private String seriesName;

    public SeriesResponse(String seriesName) {
        this.seriesName = seriesName;
    }

    public static SeriesResponse of(String seriesName) {
        return new SeriesResponse(seriesName);
    }

}
