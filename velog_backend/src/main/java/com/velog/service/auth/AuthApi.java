package com.velog.service.auth;

import com.velog.dto.auth.request.AuthRequest;
import com.velog.dto.auth.response.KaKaoUserInfoResponse;
import com.velog.dto.auth.response.KakaoAccessTokenResponse;
import org.springframework.context.annotation.Configuration;

@Configuration
public interface AuthApi {

    KakaoAccessTokenResponse tokenAuthentication(AuthRequest request);

    KaKaoUserInfoResponse getUserInfo(String accessToken);

}
