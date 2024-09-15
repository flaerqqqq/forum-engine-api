package com.example.authservice.exceptions;


/**
 * Exception thrown when a refresh token is invalid.
 * <p>
 * This exception is used to indicate that a provided refresh token is not valid,
 * typically when it is expired, malformed, or otherwise not acceptable.
 * </p>
 *
 * <p>
 * This exception extends {@link RuntimeException} and can be used to signal
 * errors related to refresh token validation in various components of the application,
 * such as authentication services.
 * </p>
 *
 * @see RuntimeException
 */
public class InvalidRefreshTokenException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidRefreshTokenException} with the specified detail message.
     *
     * @param msg the detail message, which is saved for later retrieval by the {@link Throwable#getMessage()} method
     */
    public InvalidRefreshTokenException(String msg) {
        super(msg);
    }
}