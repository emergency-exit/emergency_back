package com.velog.dto.board

import lombok.Getter
import lombok.Setter
import javax.validation.constraints.Min

@Setter
@Getter
data class RetrieveBoardRequest(
    @field:Min(1)
    val page: Int,
    @field:Min(1)
    val size: Int,
    val search: String?,
    val sort: String?
)
