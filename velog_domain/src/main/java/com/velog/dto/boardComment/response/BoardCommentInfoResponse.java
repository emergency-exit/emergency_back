package com.velog.dto.boardComment.response;

import com.velog.dto.member.response.MemberInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCommentInfoResponse {

    private Long boardId;
    private String content;
    private MemberInfoResponse memberInfoResponse;

    public BoardCommentInfoResponse(Long boardId, String content, MemberInfoResponse memberInfoResponse) {
        this.boardId = boardId;
        this.content = content;
        this.memberInfoResponse = memberInfoResponse;
    }

    public static BoardCommentInfoResponse of(Long boardId, String content, MemberInfoResponse memberInfoResponse) {
        return new BoardCommentInfoResponse(boardId, content, memberInfoResponse);
    }

}
