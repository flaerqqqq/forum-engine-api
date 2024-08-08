package com.example.authservice.handlers;

import com.example.authservice.clients.exceptions.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
