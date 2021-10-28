package com.velog.dto.boardComment.response;

import com.velog.domain.board.BoardComment;
import com.velog.dto.member.response.MemberInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardCommentInfoResponse {

    private Long commentId;
    private String content;
    private final List<BoardCommentInfoResponse> childCommentList = new ArrayList<>();
    private MemberInfoResponse memberInfoResponse;

    public BoardCommentInfoResponse(Long commentId, String content, MemberInfoResponse memberInfoResponse) {
        this.commentId = commentId;
        this.content = content;
        this.memberInfoResponse = memberInfoResponse;
    }

    public static BoardCommentInfoResponse of(BoardComment boardComment, MemberInfoResponse memberInfoResponse) {
        // TODO: 2021/10/28 이부분 member 를 어떻게 가져와야하나??
        List<BoardCommentInfoResponse> boardChildCommentList = boardComment.getChildComments().stream()
                .map(boardChildComment -> BoardCommentInfoResponse.of(boardChildComment, null))
                .collect(Collectors.toList());
        BoardCommentInfoResponse boardCommentInfoResponse = new BoardCommentInfoResponse(boardComment.getId(), boardComment.getContent(), memberInfoResponse);
        boardCommentInfoResponse.childCommentList.addAll(boardChildCommentList);
        return boardCommentInfoResponse;
    }

}