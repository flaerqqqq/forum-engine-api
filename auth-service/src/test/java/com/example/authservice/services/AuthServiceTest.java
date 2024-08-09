package com.example.authservice.services;

import com.example.authservice.clients.UserClient;
import com.example.authservice.clients.dtos.UserServiceCreateRequestDto;
import com.example.authservice.clients.dtos.UserServiceResponseDto;
import com.example.authservice.clients.exceptions.UserServiceException;
import com.example.authservice.dtos.LoginJwtResponseDto;
import com.example.authservice.dtos.LoginRequestDto;
import com.example.authservice.dtos.UserRegisterRequestDto;
import com.example.authservice.dtos.UserRegisterResponseDto;
import com.example.authservice.entities.Role;
import com.example.authservice.entities.UserRole;
import com.example.authservice.exceptions.IncorrectPasswordException;
import com.example.authservice.exceptions.RoleNotFoundException;
import com.example.authservice.repositories.RoleRepository;
import com.example.authservice.repositories.UserRoleRepository;
import com.example.authservice.services.impls.AuthServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.RuntimeErrorException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    UserRoleRepository userRoleRepository;

    @MockBean
    UserClient userClient;

    @MockBean
    AuthenticationManager authManager;

    @MockBean
    JwtService jwtService;

    @Autowired
    AuthService authService;

    private LocalDateTime createdAt = LocalDateTime.now();
    private String id = "uuid";
    private Long roleId = 1L;
    private String jwtToken = "jwtToken";

    private UserServiceCreateRequestDto userCreateRequest;
    private UserServiceResponseDto userCreateResponse;
    private Role role;
    private UserRole userRole;
    private UserRegisterRequestDto registerRequest;
    private UserRegisterResponseDto registerResponse;
    private LoginRequestDto loginRequest;
    private LoginJwtResponseDto loginResponse;

    @BeforeEach
    void setUp() {

        userCreateRequest = UserServiceCreateRequestDto.builder()
                .username("username1")
                .email("test1@example.com")
                .password("Password1234!")
                .build();
        userCreateResponse = UserServiceResponseDto.builder()
                .id(id)
                .username("username1")
                .email("test1@example.com")
                .createdAt(createdAt)
                .build();
        role = Role.builder()
                .id(roleId)
                .name(Role.RoleName.ROLE_USER)
                .build();
        userRole = UserRole.builder()
                .userId(id)
                .role(new Role())
                .build();
        registerRequest = UserRegisterRequestDto.builder()
                .username("username1")
                .email("test1@example.com")
                .password("Password1234!")
                .build();
        registerResponse = UserRegisterResponseDto.builder()
                .id(id)
                .username("username1")
                .email("test1@example.com")
                .createdAt(createdAt)
                .build();
        loginRequest = LoginRequestDto.builder()
                .username("username")
                .password("pass")
                .build();
        loginResponse = LoginJwtResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    @AfterEach
    void cleanUp() {
        userCreateRequest = null;
        userCreateResponse = null;
        userRole = null;
        registerRequest = null;
        registerResponse = null;
    }

    @Test
    void register_shouldRegister_whenUserDataIsCorrect() {
        when(userClient.create(any(UserServiceCreateRequestDto.class)))
                .thenReturn(new ResponseEntity<>(userCreateResponse, HttpStatus.CREATED));
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));

        UserRegisterResponseDto actualResponse = authService.register(registerRequest);

        assertThat(actualResponse).isEqualTo(registerResponse);
    }

    @Test
    void register_clientShouldThrow_whenUserDataIsIncorrect() {
        when(userClient.create(any(UserServiceCreateRequestDto.class)))
                .thenThrow(new RuntimeException("Feign client error"));

        assertThrows(RuntimeException.class, () ->
                authService.register(registerRequest));
    }

    @Test
    void register_shouldThrow_whenRoleIsNotFound() {
        when(userClient.create(any(UserServiceCreateRequestDto.class)))
                .thenReturn(new ResponseEntity<>(userCreateResponse, HttpStatus.CREATED));
        when(roleRepository.findByName(any(Role.RoleName.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(RoleNotFoundException.class, () ->
                authService.register(registerRequest));
    }

    @Test
    void login_shouldLogin_ifLoginDataCorrect() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("username", "pass");
        token.setAuthenticated(true);

        when(userClient.getById(anyString())).thenReturn(ResponseEntity.ok().body(userCreateResponse));
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(token);
        when(jwtService.generate(any(UserDetails.class))).thenReturn(jwtToken);

        LoginJwtResponseDto actualResponse = authService.login(loginRequest);

        assertThat(actualResponse).isEqualTo(loginResponse);
        assertThat(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()).isTrue();
    }

    @Test
    void login_shouldThrow_ifUserWithSuchUsernameDoesNotExist() {
        when(userClient.getById(anyString())).thenThrow(UserServiceException.class);

        assertThrows(UserServiceException.class, () -> authService.login(loginRequest));
    }

    @Test
    void login_shouldThrow_ifPasswordIncorrect() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("username", "pass");
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(token);

        assertThrows(IncorrectPasswordException.class, () -> authService.login(loginRequest));
    }
}
