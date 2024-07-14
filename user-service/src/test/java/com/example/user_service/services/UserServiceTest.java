package com.example.user_service.services;

import com.example.user_service.dto.UserDto;
import com.example.user_service.exceptions.EmailAlreadyInUseException;
import com.example.user_service.exceptions.UsernameAlreadyInUseException;
import com.example.user_service.mappers.UserMapper;
import com.example.user_service.model.User;
import com.example.user_service.repositories.UserRepository;
import com.example.user_service.services.impls.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    public void setup() {
        userDto = UserDto.builder()
                .username("test")
                .email("test@example.com")
                .password("Passss123!")
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
        user = User.builder()
                .username("test")
                .email("test@example.com")
                .password("Passss123!")
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
    }

    @AfterEach
    public void cleanup() {
        userDto = null;
        user = null;
    }

    @Test
    void create_shouldCreateUser_whenUsernameAndEmailNotInUse() {
        when(userMapper.fromDto(any(UserDto.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("Passss123!");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        UserDto actualResponse = userService.create(userDto);

        assertThat(actualResponse).isEqualTo(userDto);
    }

    @Test
    void create_shouldThrow_whenUsernameInUse() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThatThrownBy(() -> userService.create(userDto)).isInstanceOf(UsernameAlreadyInUseException.class);
    }

    @Test
    void create_shouldThrow_whenEmailInUse() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThatThrownBy(() -> userService.create(userDto)).isInstanceOf(EmailAlreadyInUseException.class);
    }
}
