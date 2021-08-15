package com.velog.domain.testObject;

import com.velog.domain.board.Board;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardCreator {

    public static Board create(String title, Long memberId) {
        return Board.builder()
                .title(title)
                .content("content")
                .memberId(memberId)
                .seriesId(1L)
                .isPrivate(false)
                .build();
    }

}
