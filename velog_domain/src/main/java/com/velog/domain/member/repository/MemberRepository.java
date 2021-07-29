package com.velog.domain.member.repository;

import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

}
