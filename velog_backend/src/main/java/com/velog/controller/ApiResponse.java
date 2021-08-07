package com.velog.controller;

import com.velog.exception.errorCode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final T data;
    private final String message;
    private final int code;
    private final String description;

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(data, null, 200, null);
    }

    public static ApiResponse<Object> error(ErrorCode errorCode, String description){
        return new ApiResponse<>(null, errorCode.getMessage(), errorCode.getCode(), description);
    }

}
