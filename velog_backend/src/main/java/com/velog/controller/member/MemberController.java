package com.velog.controller.member;

import com.velog.config.security.PrincipalDetails;
import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.dto.member.request.LoginRequest;
import com.velog.service.member.MemberService;
import com.velog.domain.member.Member;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public String createMember(@RequestBody CreateMemberRequest request) throws ValidationException {
        memberService.createMember(request);
        return "ok";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) throws NotFoundException {
        return memberService.login(request);
    }

    @GetMapping("/api/v1")
    public String getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return principalDetails.getMember().getId().toString();
    }

}
