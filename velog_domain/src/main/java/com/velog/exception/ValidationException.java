package com.velog.exception;

public class ValidationException extends CustomException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, String description) {
        super(message, description);
    }

}
