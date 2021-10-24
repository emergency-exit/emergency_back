package com.velog.dto.auth.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoAccessTokenResponse {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Integer expiresIn;
    private String scope;
    private Integer refreshTokenExpiresIn;

    public KakaoAccessTokenResponse(String accessToken, String tokenType, String refreshToken, Integer expiresIn, String scope, Integer refreshTokenExpiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public static KakaoAccessTokenResponse of(String accessToken, String tokenType, String refreshToken, Integer expiresIn, String scope, Integer refreshTokenExpiresIn) {
        return new KakaoAccessTokenResponse(accessToken, tokenType, refreshToken, expiresIn, scope, refreshTokenExpiresIn);
    }

}
