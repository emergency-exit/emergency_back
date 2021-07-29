package com.velog.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final T data;
    private final String message;
    private final String code;

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(data, null, null);
    }

    public static ApiResponse<Object> error(String message){
        return new ApiResponse<>(null, message, "");
    }

}
