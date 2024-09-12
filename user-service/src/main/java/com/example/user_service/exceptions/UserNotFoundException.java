package com.example.user_service.exceptions;

/**
 * Exception thrown when a user with the specified identifier is not found in the system.
 * <p>
 * This exception is used to signal that a requested user could not be located based on the provided identifier,
 * such as a user ID. It is commonly used in scenarios where user data retrieval is attempted but the user does not exist.
 * </p>
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a {@code UserNotFoundException} with the specified detail message.
     *
     * @param msg the detail message
     */
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
