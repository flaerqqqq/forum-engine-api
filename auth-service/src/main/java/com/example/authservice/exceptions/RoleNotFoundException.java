package com.example.authservice.exceptions;

/**
 * Exception thrown when a requested role is not found in the system.
 * <p>
 * This exception is used to signal that a specific role could not be located in the database or
 * during the role retrieval process. It extends {@link RuntimeException} and provides a specific message
 * indicating the nature of the error.
 * </p>
 */
public class RoleNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code RoleNotFoundException} with the specified detail message.
     *
     * @param msg the detail message explaining the reason for the exception
     */
    public RoleNotFoundException(String msg) {
        super(msg);
    }
}
