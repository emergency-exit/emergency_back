package com.velog.dto.member.response;

import com.velog.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoResponse {

    private String email;
    private String name;
    private String memberImage;
    private String velogName;
    private String description;

    public MemberInfoResponse(String email, String name, String memberImage, String velogName, String description) {
        this.email = email;
        this.name = name;
        this.memberImage = memberImage;
        this.velogName = velogName;
        this.description = description;
    }

    public static MemberInfoResponse of(Member member) {
        return new MemberInfoResponse(member.getEmail().getEmail(), member.getName(), member.getMemberImage(), member.getVelogName(), member.getDescription());
    }

    public static MemberInfoResponse deleteMember() {
        return new MemberInfoResponse(null, null, null, null, null);
    }

}
