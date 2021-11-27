package com.velog.dto.adminMember.request

import javax.validation.constraints.NotBlank

data class LoginAdminMemberRequest(
    @field:NotBlank
    var email: String = "",
    @field:NotBlank
    var password: String = ""
)
