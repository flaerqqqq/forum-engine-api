package com.example.authservice.handlers;

import com.example.authservice.exceptions.InvalidRefreshTokenException;
import com.example.authservice.exceptions.UserServiceException;
import com.example.authservice.exceptions.IncorrectPasswordException;
import com.example.authservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for managing exceptions across the application.
 * Provides centralized exception handling for various types of exceptions.
 *
 * <p>
 * - Uses {@link ControllerAdvice} to handle exceptions thrown by controllers.
 * - Handles general exceptions, user service-specific exceptions, and validation errors.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Handles all general exceptions.
     * This method captures any exception not specifically handled elsewhere and returns a generic error response.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with a status of {@link HttpStatus#INTERNAL_SERVER_ERROR}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleSideExceptions(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Handles exceptions specific to the user service, such as errors from Feign clients.
     *
     * @param ex the {@link UserServiceException} that was thrown
     * @return a {@link ResponseEntity} containing the exception message and an appropriate HTTP status
     */
    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<String> handleFeignClientExceptions(UserServiceException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatus());
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    /**
     * Handles exceptions related to user credentials, such as user not found or incorrect password.
     *
     * @param ex the {@link Exception} (either {@link UserNotFoundException} or {@link IncorrectPasswordException}) that was thrown
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with a status of {@link HttpStatus#UNAUTHORIZED}
     */
    @ExceptionHandler({
            UserNotFoundException.class,
            IncorrectPasswordException.class
    })
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.UNAUTHORIZED,ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles validation errors from method arguments.
     * This method processes validation exceptions and provides detailed information about validation errors.
     *
     * @param ex the {@link MethodArgumentNotValidException} that was thrown
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with a status of {@link HttpStatus#BAD_REQUEST}
     * @throws Exception if an error occurs during error response creation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) throws Exception {
        Map<String, String> errors = new HashMap<>();
        for (var errorObject : ex.getBindingResult().getAllErrors()) {
            if (errorObject instanceof FieldError) {
                FieldError fieldError = (FieldError) errorObject;
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            } else {
                errors.put("Error", errorObject.getDefaultMessage());
            }
        }
        ErrorResponse errorResponse = ErrorResponse.builder(
                ex, HttpStatus.BAD_REQUEST, errors.toString()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            InvalidRefreshTokenException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
