package com.example.user_service.exceptions;

public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException(String msg) {
        super(msg);
    }
}
