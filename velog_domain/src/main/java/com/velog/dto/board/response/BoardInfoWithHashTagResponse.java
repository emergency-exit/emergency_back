package com.velog.dto.board.response;

import com.velog.domain.board.Board;
import com.velog.domain.board.BoardHashTag;
import com.velog.domain.member.Member;
import com.velog.dto.hashTag.HashTagResponse;
import com.velog.dto.member.response.MemberInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardInfoWithHashTagResponse {

    private BoardInfoResponse boardInfoResponse;
    private MemberInfoResponse memberInfoResponse;
    private List<HashTagResponse> hashTagList;

    public BoardInfoWithHashTagResponse(BoardInfoResponse boardInfoResponse, MemberInfoResponse memberInfoResponse, List<HashTagResponse> hashTagList) {
        this.boardInfoResponse = boardInfoResponse;
        this.memberInfoResponse = memberInfoResponse;
        this.hashTagList = hashTagList;
    }

    public static BoardInfoWithHashTagResponse of(Board boardWithHashTag, Member member) {
        BoardInfoResponse boardInfoResponse = BoardInfoResponse.of(boardWithHashTag);
        MemberInfoResponse memberInfoResponse = MemberInfoResponse.of(member);
        List<HashTagResponse> hashTagList = boardWithHashTag.getHashTagList().stream()
                .map(HashTagResponse::of).collect(Collectors.toList());
        return new BoardInfoWithHashTagResponse(boardInfoResponse, memberInfoResponse, hashTagList);
    }

}
