package com.velog.dto.auth.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoProfile {

    private String nickname;
    private String profileImageUrl;

    public KakaoProfile(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static KakaoProfile of(String nickname, String profileImageUrl) {
        return new KakaoProfile(nickname, profileImageUrl);
    }

}
