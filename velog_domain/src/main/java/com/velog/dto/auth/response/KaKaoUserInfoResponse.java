package com.velog.dto.auth.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KaKaoUserInfoResponse {

    private KakaoUserInfoAccount kakaoAccount;

    public KaKaoUserInfoResponse(KakaoUserInfoAccount kakaoAccount) {
        this.kakaoAccount = kakaoAccount;
    }

    public static KaKaoUserInfoResponse of(String nickname, String profileImageUrl, String email) {
        KakaoProfile kakaoProfile = KakaoProfile.of(nickname, profileImageUrl);
        return new KaKaoUserInfoResponse(KakaoUserInfoAccount.of(kakaoProfile, email));
    }

}
