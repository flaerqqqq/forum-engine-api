package com.example.authservice.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging exceptions in the global exception handler.
 * This class uses Aspect-Oriented Programming (AOP) to capture and log
 * exceptions thrown by the GlobalExceptionHandler class.
 * <p>
 * - Logs side exceptions as errors.
 * - Logs other exceptions as warnings.
 * </p>
 *
 * Annotations:
 * - {@code @Slf4j}: Provides a logger instance using Lombok.
 * - {@code @Component}: Marks this class as a Spring component.
 * - {@code @Aspect}: Indicates that this class defines an Aspect for AOP.
 */
@Slf4j
@Component
@Aspect
public class ExceptionLoggingAspect {

    /**
     * Logs exceptions handled by the {@code handleSideExceptions} method of the
     * {@code GlobalExceptionHandler} class as errors.
     *
     * @param ex the exception that was thrown and handled
     * @see com.example.authservice.handlers.GlobalExceptionHandler#handleSideExceptions(Exception)
     */
    @After("execution(* com.example.authservice.handlers.GlobalExceptionHandler.handleSideExceptions(..))" +
            "&& args(ex)")
    public void handleSideExceptionsAdvice(Exception ex) {
        log.error("Caught exception: " + ex.toString());
    }

    /**
     * Logs exceptions handled by any other method in the {@code GlobalExceptionHandler} class,
     * except {@code handleSideExceptions}, as warnings.
     *
     * @param ex the exception that was thrown and handled
     * @see com.example.authservice.handlers.GlobalExceptionHandler
     */
    @After("execution(* com.example.authservice.handlers.GlobalExceptionHandler.*(..)) " +
            "&& !execution(* com.example.authservice.handlers.GlobalExceptionHandler.handleSideExceptions(..))" +
            "&& args(ex)")
    public void handleOtherExceptionsAdvice(Exception ex) {
        log.warn("Caught exception: " + ex.toString());
    }
}
