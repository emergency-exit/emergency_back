package com.velog.controller;

import com.velog.config.jwt.JwtTokenProvider;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.domain.testObject.MemberCreator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberSetUp {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected JwtTokenProvider jwtTokenProvider;

    @Autowired
    protected MemberRepository memberRepository;

    protected String token;

    protected Member member;

    @BeforeEach
    void setUp() {
        String email = "tnswh2023@gmail.com";
        token = jwtTokenProvider.createToken(email);
        member = MemberCreator.create(email, passwordEncoder.encode("tnswh2023@"));
        member.addSeries("자바");
        memberRepository.save(member);
    }

    protected void cleanup() {
        memberRepository.deleteAll();
    }

}
