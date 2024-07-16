package com.example.user_service.services;

import com.example.user_service.dto.UserDto;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto getById(String id);

}
