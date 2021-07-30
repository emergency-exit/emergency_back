package com.velog.exception;

public class ConflictException extends CustomException {

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, String description) {
        super(message, description);
    }

}
