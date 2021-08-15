package com.velog.dto.auth.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
