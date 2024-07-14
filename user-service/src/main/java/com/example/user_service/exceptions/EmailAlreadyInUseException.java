package com.example.user_service.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String msg) {
        super(msg);
    }
}
