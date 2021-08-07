package com.velog.service.member;

import com.velog.exception.ConflictException;
import com.velog.exception.NotFoundException;
import com.velog.exception.ValidationException;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberServiceUtils {

    public static void validateEmail(MemberRepository memberRepository, String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            throw new ConflictException(String.format("%s는 이미 존재하는 회원입니다.", email));
        }
    }

    public static Member findMemberByEmail(MemberRepository memberRepository, String email) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new NotFoundException(String.format("%s는 존재하지 않는 회원입니다.", email));
        }
        return member;
    }

    public static void validatePassword(PasswordEncoder passwordEncoder, String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new ValidationException("잘못된 비밀번호입니다.");
        }
    }

}
