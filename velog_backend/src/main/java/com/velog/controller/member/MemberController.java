package com.velog.controller.member;

import com.velog.config.security.PrincipalDetails;
import com.velog.controller.ApiResponse;
import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.dto.member.request.LoginRequest;
import com.velog.dto.member.request.UpdateMemberRequest;
import com.velog.dto.member.response.MyInfoResponse;
import com.velog.service.member.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping("/signup")
    public ApiResponse<String> createMember(@Valid @RequestBody CreateMemberRequest request) {
        memberService.createMember(request);
        return ApiResponse.success("ok");
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(memberService.login(request));
    }

    @ApiOperation(value = "내 정보 조회", notes = "내 정보 조회")
    @GetMapping("/api/v1/myInfo")
    public ApiResponse<MyInfoResponse> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(MyInfoResponse.of(principalDetails.getMember()));
    }

    @ApiOperation(value = "내 정보 수정", notes = "내 정보 수정")
    @PutMapping("/api/v1/myInfo/update")
    public ApiResponse<MyInfoResponse> updateMyInfo(@Valid @RequestBody UpdateMemberRequest request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(memberService.updateMember(request, principalDetails.getMember().getEmail().getEmail()));
    }

}
