package com.velog.service.member;

import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.domain.member.Member;
import com.velog.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member createMember(CreateMemberRequest request) {
        return memberRepository.save(request.toEntity());
    }

}
