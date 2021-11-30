package com.velog.domain.member.repository;

import com.velog.domain.member.AdminMember;

public interface AdminMemberRepositoryCustom {

    AdminMember findByEmail(String email);

    AdminMember findAdminById(long memberId);

}
