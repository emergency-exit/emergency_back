package com.velog.service.auth;

import com.velog.config.prefix.KaKaoAuthComponent;
import com.velog.dto.auth.request.AuthRequest;
import com.velog.dto.auth.response.KaKaoUserInfoResponse;
import com.velog.dto.auth.response.KakaoAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class KakaoAuthApiImpl implements AuthApi{

    private final WebClient webClient = WebClient.builder().build();
    private final KaKaoAuthComponent kaKaoAuthComponent;

    @Override
    public KakaoAccessTokenResponse tokenAuthentication(AuthRequest request) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(kaKaoAuthComponent.getUrl())
                        .queryParam("code", request.getCode())
                        .queryParam("redirect_uri", request.getRedirectUri())
                        .queryParam("grant_type", kaKaoAuthComponent.getGrant_type())
                        .queryParam("client_id", kaKaoAuthComponent.getClientKey())
                        .build()
                )
                .retrieve()
                .bodyToMono(KakaoAccessTokenResponse.class)
                .block();
    }

    @Override
    public KaKaoUserInfoResponse getUserInfo(String accessToken) {
        return webClient.get()
                .uri(kaKaoAuthComponent.getUserInfoUrl())
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KaKaoUserInfoResponse.class)
                .block();
    }

}
