package com.example.user_service.services;

import com.example.user_service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto getById(String id);

    Page<UserDto> getAll(Pageable pageable);

    void delete(String id);

}
