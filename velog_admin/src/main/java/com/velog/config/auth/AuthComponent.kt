package com.velog.config.auth

import com.velog.config.jwt.JwtTokenProvider
import com.velog.domain.member.repository.AdminMemberRepository
import com.velog.exception.NotFoundException
import com.velog.exception.ValidationException
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletRequest

@Component
class AuthComponent(
    private val jwtTokenProvider: JwtTokenProvider,
    private val adminMemberRepository: AdminMemberRepository
) {
    fun authCheck(request: HttpServletRequest): Boolean {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (!StringUtils.hasLength(header)) {
            throw ValidationException("잘못된 헤더 ${header} 입니다.")
        }

        val memberId = jwtTokenProvider.getSubject(header)
        if (memberId != null) {
            adminMemberRepository.findById(memberId.toLong())
                ?: throw NotFoundException("존재하지 않는 멤버 아이디 ${memberId} 입니다")
        }
        if (memberId != null) {
            request.setAttribute("memberId", memberId.toLong())
        }
        return true
    }

}