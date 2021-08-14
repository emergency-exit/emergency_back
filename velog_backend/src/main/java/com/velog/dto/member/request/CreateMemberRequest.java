package com.velog.dto.member.request;

import com.velog.domain.member.Email;
import com.velog.domain.member.Member;
import com.velog.domain.member.Password;
import com.velog.enumData.ProviderType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CreateMemberRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
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
                .email(Email.of(email))
                .password(Password.of(passwordEncoded))
                .name(name)
                .provider(ProviderType.LOCAL)
                .memberImage(memberImage)
                .build();
    }

}
