package com.velog.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/")
    fun test(): ApiResponse<String> {
        return ApiResponse.OK
    }

    @GetMapping("/ping")
    fun pong(): ApiResponse<String> {
        return ApiResponse.success("pong");
    }


}