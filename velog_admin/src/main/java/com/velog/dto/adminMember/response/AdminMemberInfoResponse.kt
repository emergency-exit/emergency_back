package com.velog.dto.adminMember.response

import com.velog.domain.member.AdminMember

data class AdminMemberInfoResponse(
    val email: String,
    val memberImage: String?
) {
    companion object {
        fun of(adminMember: AdminMember): AdminMemberInfoResponse {
            return AdminMemberInfoResponse(adminMember.email, adminMember.memberImage)
        }
    }
}
