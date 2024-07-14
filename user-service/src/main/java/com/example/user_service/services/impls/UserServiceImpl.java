package com.example.user_service.services.impls;

import com.example.user_service.dto.UserDto;
import com.example.user_service.exceptions.EmailAlreadyInUseException;
import com.example.user_service.exceptions.UsernameAlreadyInUseException;
import com.example.user_service.mappers.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repositories.UserRepository;
import com.example.user_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto create(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername()))  {
            throw new UsernameAlreadyInUseException("User with such username already exists: %s".formatted(userDto.getUsername()));
        } else if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyInUseException(("User with such email already exists: %s".formatted(userDto.getEmail())));
        }

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        User user = userMapper.fromDto(userDto);
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
}
