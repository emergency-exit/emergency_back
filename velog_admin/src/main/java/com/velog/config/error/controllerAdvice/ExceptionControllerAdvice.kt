package com.velog.config.error.controllerAdvice

import com.velog.controller.ErrorResponse
import com.velog.exception.ConflictException
import com.velog.exception.JwtException
import com.velog.exception.NotFoundException
import com.velog.exception.ValidationException
import com.velog.exception.errorCode.ErrorCode
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j
@RestControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(value = [NotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(e: NotFoundException): ErrorResponse {
        return ErrorResponse.error(e.errorCode, e.localizedMessage)
    }

    @ExceptionHandler(value = [ConflictException::class])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handlerConflict(e: ConflictException): ErrorResponse {
        return ErrorResponse.error(e.errorCode, e.localizedMessage)
    }

    @ExceptionHandler(value = [JwtException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handlerJwt(e: JwtException): ErrorResponse {
        return ErrorResponse.error(e.errorCode, e.localizedMessage)
    }

    @ExceptionHandler(value = [ValidationException::class])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handlerValidation(e: ValidationException): ErrorResponse {
        return ErrorResponse.error(e.errorCode, e.localizedMessage)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerValidation(e: MethodArgumentNotValidException): ErrorResponse {
        return ErrorResponse.error(ErrorCode.VALIDATION_EXCEPTION, e.bindingResult.allErrors.get(0).defaultMessage)
    }

    @ExceptionHandler(value = [BindException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerValidation(e: BindException): ErrorResponse {
        return ErrorResponse.error(ErrorCode.VALIDATION_EXCEPTION, e.localizedMessage)
    }

}