package com.velog.dto.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class AuthRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String redirectUri;

    public AuthRequest(String code, String redirectUri) {
        this.code = code;
        this.redirectUri = redirectUri;
    }

    public static AuthRequest of(String code, String redirectUri) {
        return new AuthRequest(code, redirectUri);
    }

}
