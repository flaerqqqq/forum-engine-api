package com.example.user_service.controllers;

import com.example.user_service.dto.UserCreateRequestDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.dto.UserResponseDto;
import com.example.user_service.mappers.UserMapper;
import com.example.user_service.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateRequestDto request) {
        UserDto requestDto = userMapper.toDto(request);
        UserDto response = userService.create(requestDto);
        return new ResponseEntity<>(userMapper.toResponseDto(response), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable String id) {
        UserDto user = userService.getById(id);
        return new ResponseEntity<>(userMapper.toResponseDto(user), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAll(Pageable pageable) {
        Page<UserDto> pageOfUsers = userService.getAll(pageable);
        if (pageOfUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(pageOfUsers.map(userMapper::toResponseDto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
