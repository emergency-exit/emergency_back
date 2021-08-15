package com.velog.dto.hashTag;

import com.velog.domain.board.BoardHashTag;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashTagResponse {

    private String hashTag;

    public HashTagResponse(String hashTag) {
        this.hashTag = hashTag;
    }

    public static HashTagResponse of(BoardHashTag boardHashTag) {
        return new HashTagResponse(boardHashTag.getHashTag());
    }

}
