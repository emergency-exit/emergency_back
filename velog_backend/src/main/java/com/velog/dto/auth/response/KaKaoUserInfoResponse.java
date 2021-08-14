package com.velog.dto.auth.response;

import com.velog.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KaKaoUserInfoResponse {

    private KakaoUserInfoAccount kakaoAccount;

    public KaKaoUserInfoResponse(KakaoUserInfoAccount kakaoAccount) {
        this.kakaoAccount = kakaoAccount;
    }

    public static KaKaoUserInfoResponse save(String nickname, String profileImageUrl, String email) {
        KakaoProfile kakaoProfile = KakaoProfile.of(nickname, profileImageUrl);
        return new KaKaoUserInfoResponse(KakaoUserInfoAccount.of(kakaoProfile, email));
    }

}
