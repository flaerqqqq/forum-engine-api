package com.example.authservice.exceptions;


public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException(String msg) {
        super(msg);
    }
}
