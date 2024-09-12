package com.example.user_service.exceptions;


import org.springframework.security.core.AuthenticationException;

/**
 * Custom exception class for handling authentication errors in the application.
 * <p>
 * This exception is used to signal issues related to authentication that may require custom handling.
 * </p>
 */
public class CustomAuthException extends AuthenticationException {

    /**
     * Constructs a {@code CustomAuthException} with the specified detail message and cause.
     *
     * @param msg   the detail message
     * @param cause the cause of the exception
     */
    public CustomAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
