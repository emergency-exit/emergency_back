package com.velog.config.exception;

public class NotFoundException extends CustomException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String description) {
        super(message, description);
    }

}
