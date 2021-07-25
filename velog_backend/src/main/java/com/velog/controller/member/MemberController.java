package com.velog.controller.member;

import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.service.member.MemberService;
import com.velog.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/v1/member")
    public String createMember(@RequestBody CreateMemberRequest request) {
        Member member = memberService.createMember(request);
        return member.getEmail();
    }

}
