package com.example.authservice.exceptions;

/**
 * Exception thrown when an incorrect password is provided during authentication.
 * <p>
 * This exception is used to indicate that the password provided by a user does not match the stored password
 * during the authentication process. It extends {@link RuntimeException} and provides a specific message
 * indicating the nature of the error.
 * </p>
 */
public class IncorrectPasswordException extends RuntimeException {

    /**
     * Constructs a new {@code IncorrectPasswordException} with the specified detail message.
     *
     * @param msg the detail message explaining the reason for the exception
     */
    public IncorrectPasswordException(String msg) {
        super(msg);
    }
}
