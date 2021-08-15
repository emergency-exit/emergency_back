package com.velog.domain.testObject;

import com.velog.domain.board.Board;
import com.velog.domain.board.BoardHashTag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashTagCreator {

    public static BoardHashTag create(String hashTag, Long memberId, Board board) {
        return BoardHashTag.of(hashTag, memberId, board);
    }

}
