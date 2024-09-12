package com.example.user_service.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueMappingStrategy;

/**
 * Configuration class for MapStruct mappers.
 * <p>
 * This configuration class defines the global settings for MapStruct mappers used in the application.
 * It specifies:
 * <ul>
 *     <li>Component model as "spring" to allow integration with the Spring framework and dependency injection.</li>
 *     <li>Injection strategy as "constructor" to prefer constructor injection for mapper dependencies.</li>
 *     <li>Null value mapping strategy as "RETURN_NULL" to return null when a mapping results in a null value.</li>
 * </ul>
 * These settings ensure consistency and integration with the Spring context for all MapStruct mappers.
 * </p>
 */
@org.mapstruct.MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public class MapperConfig {
}
