package com.example.user_service.exceptions;


import org.springframework.security.core.AuthenticationException;

public class CustomAuthException extends AuthenticationException {
    public CustomAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
