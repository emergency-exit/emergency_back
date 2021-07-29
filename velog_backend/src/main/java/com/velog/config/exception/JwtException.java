package com.velog.config.exception;

public class JwtException extends CustomException {

    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, String description) {
        super(message, description);
    }

}
