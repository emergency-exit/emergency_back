package com.velog.domain.member.repository;

import com.velog.domain.member.AdminMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<AdminMember, Long>, AdminMemberRepositoryCustom {
}
