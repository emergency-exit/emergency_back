package com.velog.service.auth;

import com.velog.dto.auth.request.AuthRequest;
import com.velog.dto.auth.response.KaKaoUserInfoResponse;
import com.velog.dto.auth.response.KakaoAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthApi authApi;

    public KaKaoUserInfoResponse kakaoAuthentication(AuthRequest request) {
        KakaoAccessTokenResponse kakaoAccessTokenResponse = authApi.tokenAuthentication(request);
        return authApi.getUserInfo(kakaoAccessTokenResponse.getAccessToken());
    }

}
