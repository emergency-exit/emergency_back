package com.velog.exception;

import com.velog.exception.errorCode.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND_EXCEPTION);
    }

}
