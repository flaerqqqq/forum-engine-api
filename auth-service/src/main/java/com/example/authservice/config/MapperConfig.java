package com.example.authservice.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueMappingStrategy;

/**
 * Configuration class for MapStruct mappers.
 * <p>
 * This configuration is shared across multiple mappers by using the {@code MapperConfig}
 * annotation to set common properties. It defines how MapStruct generates mappers and how
 * dependency injection is handled.
 * </p>
 *
 * <ul>
 *     <li>{@code componentModel = "spring"}: Configures MapStruct to generate Spring beans for mappers.</li>
 *     <li>{@code injectionStrategy = InjectionStrategy.CONSTRUCTOR}: Specifies that constructor-based dependency injection is used for mappers.</li>
 *     <li>{@code nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL}: Configures MapStruct to return {@code null} when mapping {@code null} values.</li>
 * </ul>
 *
 * <p>
 * This configuration should be used as a base configuration for other MapStruct mappers
 * in the project to ensure consistent behavior.
 * </p>
 *
 * @see org.mapstruct.Mapper
 */
@org.mapstruct.MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public class MapperConfig {
}
