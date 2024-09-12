package com.example.authservice.exceptions;

/**
 * Exception thrown when a requested user is not found in the system.
 * <p>
 * This exception indicates that a specific user could not be located in the database or
 * during the user retrieval process. It extends {@link RuntimeException} and provides a specific message
 * explaining the nature of the error.
 * </p>
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code UserNotFoundException} with the specified detail message.
     *
     * @param msg the detail message explaining the reason for the exception
     */
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
