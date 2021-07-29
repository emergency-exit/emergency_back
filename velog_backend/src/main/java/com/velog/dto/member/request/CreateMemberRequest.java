package com.velog.dto.member.request;

import com.velog.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMemberRequest {

    private String email;

    private String password;

    private String name;
    private String memberImage;

    public CreateMemberRequest(String email, String password, String name, String memberImage) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.memberImage = memberImage;
    }

    public Member toEntity(String passwordEncoded) {
        return Member.builder()
                .email(email)
                .password(passwordEncoded)
                .name(name)
                .memberImage(memberImage)
                .build();
    }

}
