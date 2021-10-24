package com.velog.exception;

import com.velog.exception.errorCode.ErrorCode;

public class ValidationException extends CustomException {

    public ValidationException(String message) {
        super(message, ErrorCode.VALIDATION_EXCEPTION);
    }

}
