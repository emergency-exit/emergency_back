package com.velog.utils

import javax.validation.constraints.NotBlank

class RetrieveRequest(
    @field:NotBlank
    var lastId: Long,
    @field:NotBlank
    var size: Int
)