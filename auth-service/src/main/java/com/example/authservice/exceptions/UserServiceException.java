package com.example.authservice.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Custom exception for handling errors from the user service.
 * <p>
 * This class extends {@link RuntimeException} to represent errors specific to the user service.
 * It includes an HTTP status code to provide additional context about the error.
 * </p>
 */
@Getter
@Setter
public class UserServiceException extends RuntimeException {

    /**
     * HTTP status code associated with the error.
     */
    private int status;

    /**
     * Constructs a new {@link UserServiceException} with the specified detail message and HTTP status code.
     *
     * @param msg the detail message
     * @param status the HTTP status code
     */
    public UserServiceException(String msg, int status) {
        super(msg);
        this.status = status;
    }
}
