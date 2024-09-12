package com.example.user_service.exceptions;

/**
 * Exception thrown when an attempt is made to create or update a user with an email
 * that is already in use by another user.
 * <p>
 * This exception indicates that the provided email address is not available for use
 * and should be handled by informing the user or performing other appropriate actions.
 * </p>
 */
public class EmailAlreadyInUseException extends RuntimeException {

    /**
     * Constructs a {@code EmailAlreadyInUseException} with the specified detail message.
     *
     * @param msg the detail message
     */
    public EmailAlreadyInUseException(String msg) {
        super(msg);
    }
}
