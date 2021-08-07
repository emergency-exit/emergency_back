package com.velog.config.exception.controllerAdvice;

import com.velog.exception.ConflictException;
import com.velog.exception.JwtException;
import com.velog.exception.NotFoundException;
import com.velog.exception.ValidationException;
import com.velog.controller.ApiResponse;
import com.velog.exception.errorCode.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiResponse<Object> handlerNotFound(NotFoundException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ApiResponse<Object> handlerConflict(ConflictException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ApiResponse<Object> handlerJwt(JwtException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ApiResponse<Object> handlerValidation(ValidationException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.error(e.getErrorCode(), e.getMessage());
    }


}
