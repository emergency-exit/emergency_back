package com.velog.exception;

import com.velog.exception.errorCode.ErrorCode;

public class ConflictException extends CustomException {

    public ConflictException(String message) {
        super(message, ErrorCode.CONFLICT_EXCEPTION);
    }

}
