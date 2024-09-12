package com.example.authservice.handlers;

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

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleSideExceptions(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<String> handleFeignClientExceptions(UserServiceException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatus());
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            IncorrectPasswordException.class
    })
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.UNAUTHORIZED,ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

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
}
