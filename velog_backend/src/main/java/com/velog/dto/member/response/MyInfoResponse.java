package com.velog.dto.member.response;

import com.velog.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyInfoResponse {

    private String email;
    private String name;
    private String memberImage;

    public MyInfoResponse(String email, String name, String memberImage) {
        this.email = email;
        this.name = name;
        this.memberImage = memberImage;
    }

    public static MyInfoResponse of(Member member) {
        return new MyInfoResponse(member.getEmail().getEmail(), member.getName(), member.getMemberImage());
    }

}
