package com.velog.config.security;

import com.velog.config.exception.NotFoundException;
import com.velog.domain.member.Member;
import com.velog.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findByEmail(username);
        if (member == null) {
            throw new NotFoundException("존재하지 않는 유저입니다.");
        }
        return new PrincipalDetails(member);
    }

}
