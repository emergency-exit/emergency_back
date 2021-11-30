package com.velog.service.adminMember

import com.velog.config.jwt.JwtTokenProvider
import com.velog.domain.member.AdminMember
import com.velog.domain.member.repository.AdminMemberRepository
import com.velog.dto.adminMember.request.CreateAdminMemberRequest
import com.velog.dto.adminMember.request.LoginAdminMemberRequest
import com.velog.dto.adminMember.response.AdminMemberInfoResponse
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
        validatePassword(request.password, adminMember.password)
        return jwtTokenProvider.createToken(adminMember.id.toString())
    }

    @Transactional
    open fun getMyInfo(memberId: Long): AdminMemberInfoResponse {
        val adminMember: AdminMember = (adminMemberRepository.findAdminById(memberId)
            ?: throw NotFoundException("${memberId} 는 존재하지 않는 멤버입니다."))
        println("adminMemberRepository = ${adminMember}")
        return AdminMemberInfoResponse.of(adminMember)
    }

    private fun validatePassword(password: String, encodedPassword: String) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw ValidationException("알맞지 않은 비밀번호입니다.")
        }
    }

}