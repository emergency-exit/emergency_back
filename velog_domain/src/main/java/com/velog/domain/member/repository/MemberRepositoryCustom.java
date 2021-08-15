package com.velog.domain.member.repository;

import com.velog.domain.member.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Member findByEmail(String email);

    Optional<Member> findMemberById(Long memberId);

}
