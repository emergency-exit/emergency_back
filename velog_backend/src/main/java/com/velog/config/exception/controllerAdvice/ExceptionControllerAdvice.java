package com.velog.config.exception.controllerAdvice;

import com.velog.exception.ConflictException;
import com.velog.exception.JwtException;
import com.velog.exception.NotFoundException;
import com.velog.exception.ValidationException;
import com.velog.controller.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    protected ApiResponse<Object> handlerNotFound(NotFoundException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    protected ApiResponse<Object> handlerNotFound(ConflictException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    protected ApiResponse<Object> handlerNotFound(JwtException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    protected ApiResponse<Object> handlerNotFound(ValidationException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(e.getMessage());
    }


}
