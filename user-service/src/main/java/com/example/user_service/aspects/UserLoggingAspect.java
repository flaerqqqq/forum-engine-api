package com.example.user_service.aspects;

import com.example.user_service.dto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UserLoggingAspect {

    @Around("execution(* com.example.user_service.controllers.UserController.create(..))")
    public Object logCreateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Creating a new user...");
        Object result = joinPoint.proceed();
        String userId = ((UserResponseDto)result).getId();
        log.info("User created successfully with id: {}.", userId);
        return result;
    }

    @Around("execution(* com.example.user_service.controllers.UserController.getById(..))")
    public Object logGetByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object id = joinPoint.getArgs()[0];
        log.info("Retrieving user by ID: {}", id);
        Object result = joinPoint.proceed();
        log.info("User with ID {} retrieved successfully.", id);
        return result;
    }

    @Around("execution(* com.example.user_service.controllers.UserController.getAll(..))")
    public Object logGetAllMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Retrieving all users...");
        Object result = joinPoint.proceed();
        log.info("User list retrieved successfully.");
        return result;
    }

    @Around("execution(* com.example.user_service.controllers.UserController.delete(..))")
    public Object logDeleteMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object id = joinPoint.getArgs()[0];
        log.info("Deleting user with ID: {}", id);
        Object result = joinPoint.proceed();
        log.info("User with ID {} deleted successfully.", id);
        return result;
    }
}
