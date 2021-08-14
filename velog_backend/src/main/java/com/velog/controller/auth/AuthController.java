package com.velog.controller.auth;

import com.velog.controller.ApiResponse;
import com.velog.dto.auth.request.AuthRequest;
import com.velog.dto.auth.response.KaKaoUserInfoResponse;
import com.velog.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // test로 code 받기 위한 매핑
    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String testOauth(String code) {
        return String.format("카카오 인증온료 코드: %s", code);
    }

    @PostMapping("/auth/kakao")
    public ApiResponse<KaKaoUserInfoResponse> kakaoAuthentication(@RequestBody AuthRequest request) {
        return ApiResponse.success(authService.kakaoAuthentication(request));
    }

}
