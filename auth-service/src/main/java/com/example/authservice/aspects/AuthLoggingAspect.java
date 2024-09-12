package com.example.authservice.aspects;

import com.example.authservice.controllers.AuthController;
import com.example.authservice.dtos.LoginRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
import com.example.authservice.entities.AuthUser;
import com.example.authservice.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging user registration and login actions in the {@link AuthController}.
 * This aspect logs the start and completion of user registration and login processes.
 * <p>
 * - Logs when a user is being registered.
 * - Logs when a user is being logged in.
 * </p>
 *
 * Annotations:
 * - {@code @Slf4j}: Provides a logger instance using Lombok.
 * - {@code @Component}: Marks this class as a Spring component.
 * - {@code @Aspect}: Indicates that this class defines an Aspect for AOP.
 * - {@code @RequiredArgsConstructor}: Automatically generates a constructor for the {@code AuthUserRepository} dependency.
 */
@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AuthLoggingAspect {

    private final AuthUserRepository authUserRepository;

    /**
     * Logs the user registration process.
     * This method is executed around the {@code register} method of the {@code AuthController}.
     * It logs the start of the registration process, proceeds with the registration, and logs the user ID upon successful registration.
     *
     * @param joinPoint the join point representing the {@code register} method
     * @return the result of the {@code register} method, typically a {@code ResponseEntity<UserRegisterResponseDto>}
     * @throws Throwable if an error occurs during method execution
     * @see AuthController#register(..)
     */
    @Around("execution(* com.example.authservice.controllers.AuthController.register(..))")
    public Object aroundRegister(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Registering a new user...");
        Object response = joinPoint.proceed();
        String userId = ((ResponseEntity<UserRegisterResponseDto>)response).getBody().getId();
        log.info("User has been successfully registered with id: {}", userId);
        return response;

    }


    /**
     * Logs the user login process.
     * This method is executed around the {@code login} method of the {@code AuthController}.
     * It logs the start of the login process, proceeds with the login, and logs the user ID upon successful login.
     *
     * @param joinPoint the join point representing the {@code login} method
     * @return the result of the {@code login} method
     * @throws Throwable if an error occurs during method execution
     * @see AuthController#login(..)
     */
    @Around("execution(* com.example.authservice.controllers.AuthController.login(..))")
    public Object aroundLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        LoginRequestDto loginRequest = (LoginRequestDto) joinPoint.getArgs()[0];
        log.info("Logging a user...");
        Object response = joinPoint.proceed();
        AuthUser authUser = authUserRepository.findByUsername(loginRequest.getUsername()).get(); // If it reached this line, it means that user is present.
        log.info("User has been successfully logged in with id: {}", authUser.getId());
        return response;
    }
}
