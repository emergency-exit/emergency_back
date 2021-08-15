package com.velog.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.velog.domain.member.Member;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.velog.domain.member.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findByEmail(String email) {
        return queryFactory.selectFrom(member)
                .where(
                        member.email.email.eq(email)
                )
                .fetchOne();
    }

    @Override
    public Optional<Member> findMemberById(Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(member.id.eq(memberId))
                .fetchOne()
        );
    }

}
