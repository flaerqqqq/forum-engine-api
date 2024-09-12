package com.example.user_service.handlers;

import com.example.user_service.exceptions.EmailAlreadyInUseException;
import com.example.user_service.exceptions.UsernameAlreadyInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the user service application.
 * <p>
 * This class handles exceptions thrown by the application and returns appropriate HTTP responses.
 * It uses the {@link ControllerAdvice} annotation to handle exceptions globally across all controllers.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all general exceptions and returns a response with HTTP status 500 (Internal Server Error).
     * <p>
     * This method catches all exceptions not specifically handled by other methods and returns a generic error response.
     * </p>
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity with the error response and HTTP status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleSideExceptions(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles exceptions related to email or username already in use and returns a response with HTTP status 400 (Bad Request).
     * <p>
     * This method specifically handles {@link EmailAlreadyInUseException} and {@link UsernameAlreadyInUseException}
     * and provides a more specific error response for these cases.
     * </p>
     *
     * @param ex the exception that was thrown
     * @return a ResponseEntity with the error response and HTTP status 400
     */
    @ExceptionHandler({
            EmailAlreadyInUseException.class,
            UsernameAlreadyInUseException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
