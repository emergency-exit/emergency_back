package com.velog.domain.testObject;

import com.velog.domain.member.Email;
import com.velog.domain.member.Member;
import com.velog.domain.member.Password;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCreator {

    public static Member create() {
        return Member.builder().email(Email.of("tnswh2023@gmail.com"))
                .name("tnswh")
                .password(Password.of("tnswh2023@"))
                .build();
    }

}
