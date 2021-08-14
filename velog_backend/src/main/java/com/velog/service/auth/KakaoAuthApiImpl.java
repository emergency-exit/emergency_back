package com.velog.service.auth;

import com.velog.config.prefix.KaKaoAuthComponent;
import com.velog.dto.auth.request.AuthRequest;
import com.velog.dto.auth.response.KaKaoUserInfoResponse;
import com.velog.dto.auth.response.KakaoAccessTokenResponse;
import com.velog.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class KakaoAuthApiImpl implements AuthApi{

    private final KaKaoAuthComponent kaKaoAuthComponent;

    @Override
    public KakaoAccessTokenResponse tokenAuthentication(AuthRequest request) {
        return WebClient.create(kaKaoAuthComponent.getUrl())
                .get()
                .uri(uriBuilder -> uriBuilder.path("")
                        .queryParam("code", request.getCode())
                        .queryParam("redirect_uri", request.getRedirectUri())
                        .queryParam("grant_type", kaKaoAuthComponent.getGrant_type())
                        .queryParam("client_id", kaKaoAuthComponent.getClientKey())
                        .build()
                )
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ValidationException("잘못된 입력")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new ValidationException("카카오 api 시도중 애러")))
                .bodyToMono(KakaoAccessTokenResponse.class)
                .block();
    }

    @Override
    public KaKaoUserInfoResponse getUserInfo(String accessToken) {
        return WebClient.create()
                .get()
                .uri(kaKaoAuthComponent.getUserInfoUrl())
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ValidationException("잘못된 입력")))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new ValidationException("카카오 api 시도중 애러")))
                .bodyToMono(KaKaoUserInfoResponse.class)
                .block();
    }

}
