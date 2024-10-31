package com.example.authservice.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class EmailConfirmEventProducerLoggingAspect {

    @Around("execution(* com.example.authservice.producers.EmailConfirmEventProducer.sendEmailConfirmation(..))")
    public Object aroundSendEmailConfirmation(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        log.info("Sending email confirmation data to kafka: uuid={}, email={} ...", args[0], args[1]);
        Object result = joinPoint.proceed();
        log.info("Email confirmation data has been successfully sent");
        return result;
    }
}
