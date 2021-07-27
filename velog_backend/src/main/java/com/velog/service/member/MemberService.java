package com.velog.service.member;

import com.velog.config.jwt.JwtTokenProvider;
import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.domain.member.Member;
import com.velog.domain.member.MemberRepository;
import com.velog.dto.member.request.LoginRequest;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public Member createMember(CreateMemberRequest request) throws ValidationException {
        MemberServiceUtils.validateEmail(memberRepository, request.getEmail());
        String passwordEncoded = passwordEncoder.encode(request.getPassword());
        return memberRepository.save(request.toEntity(passwordEncoded));
    }

    @Transactional
    public String login(LoginRequest request) throws NotFoundException {
        Member member = MemberServiceUtils.findMemberByEmail(memberRepository, request.getEmail());
        MemberServiceUtils.validatePassword(passwordEncoder, request.getPassword(), member.getPassword());
        return jwtTokenProvider.createToken(member.getEmail());
    }

}
