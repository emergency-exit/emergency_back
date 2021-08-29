package com.velog.domain.testObject;

import com.velog.domain.board.BoardComment;

public class BoardCommentCreator {

    public static BoardComment create(Long boardId, Long memberId, String content) {
        return new BoardComment(boardId, memberId, content);
    }

}
