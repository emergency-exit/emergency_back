package com.velog.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final T data;
    private final int code;

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(data, 200);
    }

    public static final ApiResponse<String> OK = new ApiResponse<>("ok", 200);

}
