package com.velog.dto.board.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRetrieveResponse {

    private Long boardId;

    private String title;

    private String content;

    private int likeCount;

    private String boardThumbnailUrl;

    private Long memberId;

    private String name;

    private String memberImage;
}
