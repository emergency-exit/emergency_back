package com.velog.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.velog.domain.member.AdminMember;
import com.velog.domain.member.QAdminMember;
import lombok.RequiredArgsConstructor;

import static com.velog.domain.member.QAdminMember.adminMember;

@RequiredArgsConstructor
public class AdminMemberRepositoryCustomImpl implements AdminMemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public AdminMember findByEmail(String email) {
        return queryFactory.selectFrom(adminMember)
                .where(
                        adminMember.email.eq(email)
                )
                .fetchOne();
    }

}
