package com.velog.service.adminMember

import com.velog.config.jwt.JwtTokenProvider
import com.velog.domain.member.AdminMember
import com.velog.domain.member.repository.AdminMemberRepository
import com.velog.dto.adminMember.request.CreateAdminMemberRequest
import com.velog.dto.adminMember.request.LoginAdminMemberRequest
import com.velog.exception.NotFoundException
import com.velog.exception.ValidationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class AdminMemberService(
    private val adminMemberRepository: AdminMemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
    ) {

    @Transactional
    open fun adminMemberSignUp(request: CreateAdminMemberRequest): AdminMember {
        val encodePassword = passwordEncoder.encode(request.password)
        return adminMemberRepository.save(request.toEntity(encodePassword))
    }

    @Transactional
    open fun adminMemberLogin(request: LoginAdminMemberRequest): String {
        val adminMember: AdminMember = adminMemberRepository.findByEmail(request.email)
            ?: throw NotFoundException("${request.email}은 존재하지 않는 이메일 입니다.")
        if (!passwordEncoder.matches(request.password, adminMember.password)) {
            throw ValidationException("알맞지 않은 비밀번호입니다.")
        }
        return jwtTokenProvider.createToken(adminMember.id.toString())
    }

}