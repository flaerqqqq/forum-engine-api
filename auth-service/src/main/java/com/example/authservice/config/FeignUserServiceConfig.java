package com.example.authservice.config;

import com.example.authservice.exceptions.UserServiceErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignUserServiceConfig {

    @Bean
    public ErrorDecoder userServiceErrorDecoder() {
        return new UserServiceErrorDecoder();
    }
}
