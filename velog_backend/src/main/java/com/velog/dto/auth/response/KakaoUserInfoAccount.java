package com.velog.dto.auth.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoAccount {

    private KakaoProfile profile;
    private String email;

    public KakaoUserInfoAccount(KakaoProfile profile, String email) {
        this.profile = profile;
        this.email = email;
    }

    public static KakaoUserInfoAccount of(KakaoProfile profile, String email) {
        return new KakaoUserInfoAccount(profile, email);
    }

}
