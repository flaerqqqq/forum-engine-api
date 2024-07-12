package com.example.user_service.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.NullValueMappingStrategy;

@org.mapstruct.MapperConfig(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public class MapperConfig {
}
