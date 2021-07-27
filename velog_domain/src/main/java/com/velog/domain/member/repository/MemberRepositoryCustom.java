package com.velog.domain.member.repository;

import com.velog.domain.member.Member;

public interface MemberRepositoryCustom {

    Member findByEmail(String email);

}
