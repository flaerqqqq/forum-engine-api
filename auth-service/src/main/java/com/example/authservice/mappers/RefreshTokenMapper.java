package com.example.authservice.mappers;

import com.example.authservice.config.MapperConfig;
import com.example.authservice.dtos.RefreshTokenDto;
import com.example.authservice.entities.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface RefreshTokenMapper {

    @Mapping(target = "userId", expression = "java(refreshToken.getAuthUser().getId())")
    public abstract RefreshTokenDto toDto(RefreshToken refreshToken);
}
