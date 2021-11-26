package com.velog.dto.adminMember.request

import com.velog.domain.member.AdminMember
import javax.validation.constraints.NotBlank

data class CreateAdminMemberRequest(

    @field:NotBlank
    var email: String = "",

    @field:NotBlank
    var password: String = ""
) {

    fun toEntity(encodedPassword: String): AdminMember {
        return AdminMember(email, encodedPassword, null)
    }

}
