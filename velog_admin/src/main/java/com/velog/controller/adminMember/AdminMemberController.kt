package com.velog.controller.adminMember

import com.velog.config.argument.MemberId
import com.velog.config.auth.Auth
import com.velog.controller.ApiResponse
import com.velog.dto.adminMember.request.CreateAdminMemberRequest
import com.velog.dto.adminMember.request.LoginAdminMemberRequest
import com.velog.service.adminMember.AdminMemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AdminMemberController(private val adminMemberService: AdminMemberService) {

    @PostMapping("/api/v1/user/member/signup")
    fun adminMemberSignUp(@RequestBody @Valid request: CreateAdminMemberRequest): ApiResponse<String> {
        adminMemberService.adminMemberSignUp(request)
        return ApiResponse.OK
    }

    @PostMapping("/api/v1/user/member/login")
    fun adminMemberLogin(@RequestBody @Valid request: LoginAdminMemberRequest): ApiResponse<String> {
        return ApiResponse.success(adminMemberService.adminMemberLogin(request))
    }

    @Auth
    @GetMapping("/api/v1/user/member")
    fun getMyInfo(@MemberId memberId: Long) {
        if (memberId == null) {
            println("hello")
        }
        else {
            println("memberId = ${memberId}")
        }
    }

}