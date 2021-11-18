package com.velog.controller

data class ApiResponse<T>(
        val data: T?,
        val code: Int,
) {
    companion object {

        val OK = success("ok")

        public fun<T> success(data: T): ApiResponse<T> {
            return ApiResponse(data, 200)
        }

    }

}
