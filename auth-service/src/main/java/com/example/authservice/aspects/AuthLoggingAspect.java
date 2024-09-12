package com.example.authservice.aspects;

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

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AuthLoggingAspect {

    private final AuthUserRepository authUserRepository;

    @Around("execution(* com.example.authservice.controllers.AuthController.register(..))")
    public Object aroundRegister(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Registering a new user...");
        Object response = joinPoint.proceed();
        String userId = ((ResponseEntity<UserRegisterResponseDto>)response).getBody().getId();
        log.info("User has been successfully registered with id: {}", userId);
        return response;

    }

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
