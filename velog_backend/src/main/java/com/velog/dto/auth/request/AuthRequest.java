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

}
