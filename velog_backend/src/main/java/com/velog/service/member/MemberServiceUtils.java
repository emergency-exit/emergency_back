package com.velog.service.member;

import com.velog.domain.member.Member;
import com.velog.domain.member.MemberRepository;
import javassist.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.bind.ValidationException;

public class MemberServiceUtils {

    public static void validateEmail(MemberRepository memberRepository, String email) throws ValidationException {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            throw new ValidationException("존재하는 않는 회원");
        }
    }

    public static Member findMemberByEmail(MemberRepository memberRepository, String email) throws NotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new NotFoundException("존재하지 않는 회원");
        }
        return member;
    }

    public static void validatePassword(PasswordEncoder passwordEncoder, String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new IllegalArgumentException("잘못된 비밀번호");
        }
    }

}
