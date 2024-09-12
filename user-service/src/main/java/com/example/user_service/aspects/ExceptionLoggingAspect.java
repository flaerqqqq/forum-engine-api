package com.example.user_service.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging exceptions thrown by the global exception handler.
 * <p>
 * This aspect uses Aspect-Oriented Programming (AOP) to intercept method executions
 * in the {@link com.example.user_service.handlers.GlobalExceptionHandler} class.
 * It logs exceptions with different levels based on the method being executed.
 * </p>
 */
@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

    /**
     * Logs exceptions thrown by the {@code handleSideExceptions} method of the global exception handler.
     * <p>
     * This advice is triggered after the execution of the {@code handleSideExceptions} method,
     * logging the exception at the error level.
     * </p>
     *
     * @param ex the exception thrown by the method, which is logged at error level
     */
    @After("execution(* com.example.user_service.handlers.GlobalExceptionHandler.handleSideExceptions(..))" +
            "&& args(ex)")
    public void handleSideExceptionsAdvice(Exception ex) {
        log.error("Caught exception: " + ex.toString());
    }

    /**
     * Logs exceptions thrown by any method of the global exception handler, except {@code handleSideExceptions}.
     * <p>
     * This advice is triggered after the execution of any method in the global exception handler,
     * except for {@code handleSideExceptions}, logging the exception at the warning level.
     * </p>
     *
     * @param ex the exception thrown by the method, which is logged at warning level
     */
    @After("execution(* com.example.user_service.handlers.GlobalExceptionHandler.*(..)) " +
            "&& !execution(* com.example.user_service.handlers.GlobalExceptionHandler.handleSideExceptions(..))" +
            "&& args(ex)")
    public void handleOtherExceptionsAdvice(Exception ex) {
        log.warn("Caught exception: {}", ex.toString());
    }
}
