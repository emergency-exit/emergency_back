package com.velog.service.member;

import com.velog.config.jwt.JwtTokenProvider;
import com.velog.domain.member.Password;
import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.dto.member.request.LoginRequest;
import com.velog.dto.member.request.UpdateMemberRequest;
import com.velog.dto.member.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(CreateMemberRequest request) {
        MemberServiceUtils.validateEmail(memberRepository, request.getEmail());
        Password password = Password.validationPassword(request.getPassword());
        String passwordEncoded = passwordEncoder.encode(password.getPassword());
        memberRepository.save(request.toEntity(passwordEncoded));
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        Member member = MemberServiceUtils.findMemberByEmail(memberRepository, request.getEmail());
        MemberServiceUtils.validatePassword(passwordEncoder, request.getPassword(), member.getPassword().getPassword());
        return jwtTokenProvider.createToken(member.getEmail().getEmail());
    }

    @Transactional
    public MemberInfoResponse updateMember(UpdateMemberRequest request, String email) {
        Member member = MemberServiceUtils.findMemberByEmail(memberRepository, email);
        member.updateInfo(request.getDescription(), request.getName(), request.getVelogName());
        return MemberInfoResponse.of(member);
    }

}
