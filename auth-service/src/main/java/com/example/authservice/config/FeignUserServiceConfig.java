package com.example.authservice.config;

import com.example.authservice.exceptions.UserServiceErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for Feign client error handling.
 * <p>
 * This class configures the error decoder used by Feign clients to handle errors
 * when interacting with remote services. It provides a custom implementation of
 * {@link ErrorDecoder} for handling errors specific to the user service.
 * </p>
 */
@Configuration
public class FeignUserServiceConfig {

    /**
     * Creates and returns a custom {@link ErrorDecoder} bean.
     * <p>
     * The {@link UserServiceErrorDecoder} is used to handle and decode errors
     * returned by the user service. This allows for custom error handling logic
     * tailored to the application's needs.
     * </p>
     *
     * @return an instance of {@link UserServiceErrorDecoder}
     */
    @Bean
    public ErrorDecoder userServiceErrorDecoder() {
        return new UserServiceErrorDecoder();
    }
}
