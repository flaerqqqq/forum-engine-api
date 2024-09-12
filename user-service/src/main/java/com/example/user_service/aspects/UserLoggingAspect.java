package com.example.user_service.aspects;

import com.example.user_service.dto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging user-related operations in the {@link com.example.user_service.controllers.UserController}.
 * <p>
 * This aspect uses Aspect-Oriented Programming (AOP) to intercept method executions
 * in the {@code UserController} class. It logs method calls and their results for various
 * user-related operations such as creating, retrieving, and deleting users.
 * </p>
 */
@Slf4j
@Aspect
@Component
public class UserLoggingAspect {


    /**
     * Logs the creation of a new user.
     * <p>
     * This advice is triggered around the execution of the {@code create} method in the {@code UserController}.
     * It logs a message before proceeding with the method execution and logs the user ID after successful creation.
     * </p>
     *
     * @param joinPoint the join point representing the {@code create} method execution
     * @return the result of the {@code create} method execution
     * @throws Throwable if an error occurs during method execution
     */
    @Around("execution(* com.example.user_service.controllers.UserController.create(..))")
    public Object logCreateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Creating a new user...");
        Object result = joinPoint.proceed();
        String userId = ((ResponseEntity<UserResponseDto>)result).getBody().getId();
        log.info("User created successfully with id: {}.", userId);
        return result;
    }

    /**
     * Logs the retrieval of a user by ID.
     * <p>
     * This advice is triggered around the execution of the {@code getById} method in the {@code UserController}.
     * It logs a message before proceeding with the method execution and logs a success message after retrieving the user.
     * </p>
     *
     * @param joinPoint the join point representing the {@code getById} method execution
     * @return the result of the {@code getById} method execution
     * @throws Throwable if an error occurs during method execution
     */
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
