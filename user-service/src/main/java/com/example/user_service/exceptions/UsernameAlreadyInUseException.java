package com.example.user_service.exceptions;

/**
 * Exception thrown when an attempt is made to register or update a user with a username that is already in use.
 * <p>
 * This exception is used to indicate that the username provided for a user already exists in the system
 * and cannot be used for a new user or updated user record.
 * </p>
 */
public class UsernameAlreadyInUseException extends RuntimeException {

    /**
     * Constructs an {@code UsernameAlreadyInUseException} with the specified detail message.
     *
     * @param msg the detail message
     */
    public UsernameAlreadyInUseException(String msg) {
        super(msg);
    }
}
