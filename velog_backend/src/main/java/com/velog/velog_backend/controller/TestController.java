package com.velog.velog_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("ping")
    public String pong() {
        return "pong";
    }

}
