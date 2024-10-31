package com.example.authservice.aspects;

import com.example.authservice.producers.EmailConfirmEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging email confirmation events in the producer.
 * <p>
 * This aspect intercepts the {@link EmailConfirmEventProducer#sendEmailConfirmation(String, String)} method
 * to log the details of the email confirmation event before and after the method execution.
 * It uses Aspect-Oriented Programming (AOP) to provide a cross-cutting concern without modifying the core logic.
 * </p>
 */
@Slf4j
@Component
@Aspect
public class EmailConfirmEventProducerLoggingAspect {

    /**
     * Intercepts the execution of the sendEmailConfirmation method.
     * <p>
     * This method logs the UUID and email being sent for confirmation before proceeding
     * with the actual method execution. After the method completes, it logs a success message.
     * </p>
     *
     * @param joinPoint provides reflective access to the join point (method execution).
     * @return the result of the intercepted method execution.
     * @throws Throwable if an error occurs during method execution.
     */
    @Around("execution(* com.example.authservice.producers.EmailConfirmEventProducer.sendEmailConfirmation(..))")
    public Object aroundSendEmailConfirmation(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        log.info("Sending email confirmation data to Kafka: uuid={}, email={} ...", args[0], args[1]);
        Object result = joinPoint.proceed();
        log.info("Email confirmation data has been successfully sent");
        return result;
    }
}
