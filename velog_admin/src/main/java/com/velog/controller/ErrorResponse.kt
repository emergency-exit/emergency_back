package com.velog.controller

import com.velog.exception.errorCode.ErrorCode
import lombok.Getter
import lombok.RequiredArgsConstructor

@Getter
@RequiredArgsConstructor
data class ErrorResponse(
    val code:Int,
    val message: String,
    val description: String
) {
    companion object {
        fun error(errorCode: ErrorCode, description: String): ErrorResponse {
            return ErrorResponse(errorCode.code, errorCode.message, description)
        }
    }
}
