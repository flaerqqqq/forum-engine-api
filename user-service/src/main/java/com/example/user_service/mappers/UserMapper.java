package com.example.user_service.mappers;

import com.example.user_service.config.MapperConfig;
import com.example.user_service.dto.UserCreateRequestDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.dto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserDto toDto(UserCreateRequestDto request);

    UserResponseDto toResponseDto(UserDto userDto);
}
