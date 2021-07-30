package com.velog.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private String description;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, String description) {
        super(message);
        this.description = description;
    }

}
