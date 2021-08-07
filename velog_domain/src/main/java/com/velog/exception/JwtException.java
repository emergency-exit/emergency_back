package com.velog.exception;

import com.velog.exception.errorCode.ErrorCode;

public class JwtException extends CustomException {

    public JwtException(String message) {
        super(message, ErrorCode.JWT_UNAUTHORIZED_EXCEPTION);
    }

}
