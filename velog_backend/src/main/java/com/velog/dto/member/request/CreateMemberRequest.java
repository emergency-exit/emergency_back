package com.velog.dto.member.request;

import com.velog.domain.member.Member;
import lombok.Getter;

@Getter
public class CreateMemberRequest {

    private String email;
    private String password;
    private String name;
    private String memberImage;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .memberImage(memberImage)
                .build();
    }

}
