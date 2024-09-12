package com.example.authservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining application-wide beans.
 * <p>
 * This class provides configuration for beans used throughout the application.
 * Currently, it configures a {@link ModelMapper} bean to facilitate object mapping.
 * </p>
 */
@Configuration
public class BeanConfig {

    /**
     * Creates and returns a {@link ModelMapper} bean.
     * <p>
     * The {@link ModelMapper} is used for mapping between different object models,
     * simplifying the process of converting data between different formats or structures.
     * </p>
     *
     * @return a new instance of {@link ModelMapper}
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
