package com.example.user_service.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

    @After("execution(* com.example.user_service.handlers.GlobalExceptionHandler.handleSideExceptions(..))" +
            "&& args(ex)")
    public void handleSideExceptionsAdvice(Exception ex) {
        log.error("Caught exception: " + ex.toString());
    }

    @After("execution(* com.example.user_service.handlers.GlobalExceptionHandler.*(..)) " +
            "&& !execution(* com.example.user_service.handlers.GlobalExceptionHandler.handleSideExceptions(..))" +
            "&& args(ex)")
    public void handleOtherExceptionsAdvice(Exception ex) {
        log.warn("Caught exception: {}", ex.toString());
    }
}
