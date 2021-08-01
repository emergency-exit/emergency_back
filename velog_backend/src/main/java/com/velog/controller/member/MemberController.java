package com.velog.controller.member;

import com.velog.config.security.PrincipalDetails;
import com.velog.controller.ApiResponse;
import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.dto.member.request.LoginRequest;
import com.velog.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<String> createMember(@Valid @RequestBody CreateMemberRequest request) {
        memberService.createMember(request);
        return ApiResponse.success("ok");
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(memberService.login(request));
    }

    @GetMapping("/api/v1")
    public String getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return principalDetails.getMember().getId().toString();
    }

}
