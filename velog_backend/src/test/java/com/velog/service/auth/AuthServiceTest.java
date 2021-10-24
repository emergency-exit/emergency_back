package com.velog.service.auth;

import com.velog.dto.auth.request.AuthRequest;
import com.velog.dto.auth.response.KaKaoUserInfoResponse;
import com.velog.dto.auth.response.KakaoAccessTokenResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(new MockAuthApi());
    }

    @Test
    void 카카오_인증_테스트() {
        // given
        AuthRequest request = AuthRequest.of("code", "redirectUri");

        // when
        KaKaoUserInfoResponse response = authService.kakaoAuthentication(request);

        // then
        Assertions.assertThat(response.getKakaoAccount().getEmail()).isEqualTo("email");
        Assertions.assertThat(response.getKakaoAccount().getProfile().getNickname()).isEqualTo("nickname");
        Assertions.assertThat(response.getKakaoAccount().getProfile().getProfileImageUrl()).isEqualTo("profileImageUrl");
    }

    private static class MockAuthApi implements AuthApi {

        @Override
        public KakaoAccessTokenResponse tokenAuthentication(AuthRequest request) {
            return KakaoAccessTokenResponse.of("accessToken", "tokenType", "refreshToken", 10000, "scope", 10000);
        }

        @Override
        public KaKaoUserInfoResponse getUserInfo(String accessToken) {
            return KaKaoUserInfoResponse.of("nickname", "profileImageUrl", "email");
        }

    }



}
