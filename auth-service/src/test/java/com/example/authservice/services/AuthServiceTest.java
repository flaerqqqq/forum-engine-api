package com.example.authservice.services;

import com.example.authservice.clients.UserClient;
import com.example.authservice.dtos.*;
import com.example.authservice.entities.RefreshToken;
import com.example.authservice.exceptions.InvalidRefreshTokenException;
import com.example.authservice.producers.EmailConfirmEventProducer;
import com.example.authservice.repositories.RefreshTokenRepository;
import com.example.authservice.request.UserServiceCreateRequestDto;
import com.example.authservice.response.UserServiceResponseDto;
import com.example.authservice.entities.AuthUser;
import com.example.authservice.entities.Role;
import com.example.authservice.entities.UserRole;
import com.example.authservice.exceptions.IncorrectPasswordException;
import com.example.authservice.exceptions.RoleNotFoundException;
import com.example.authservice.exceptions.UserNotFoundException;
import com.example.authservice.repositories.AuthUserRepository;
import com.example.authservice.repositories.RoleRepository;
import com.example.authservice.repositories.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles({"test","dev"})
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

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    AuthUserRepository authUserRepository;

    @MockBean
    RefreshTokenService refreshTokenService;

    @MockBean
    RefreshTokenRepository refreshTokenRepository;

    @MockBean
    EmailConfirmEventProducer emailConfirmEventProducer;

    @Autowired
    AuthService authService;

    private LocalDateTime createdAt = LocalDateTime.now();
    private String id = "uuid";
    private Long roleId = 1L;
    private String userId = "id";
    private String jwtToken = "jwtToken";
    private String refreshToken = "refreshToken";
    private String password = "password";

    private UserServiceResponseDto userCreateResponse;
    private Role role;
    private UserRole userRole;
    private UserRegisterRequestDto registerRequest;
    private UserRegisterResponseDto registerResponse;
    private LoginRequestDto loginRequest;
    private LoginJwtResponseDto loginResponse;
    private AuthUser authUser;
    private RefreshTokenDto refreshTokenDto;
    private RefreshToken refreshTokenEntity;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .id(roleId)
                .name(Role.RoleName.ROLE_USER)
                .build();

        authUser = AuthUser.builder()
                .id(userId)
                .username("username")
                .password(password)
                .build();

        userRole = UserRole.builder()
                .authUser(authUser)
                .role(role)
                .build();

        authUser.setUserRoles(Collections.singletonList(userRole));

        userCreateResponse = UserServiceResponseDto.builder()
                .id(id)
                .username("username1")
                .email("test1@example.com")
                .createdAt(createdAt)
                .build();

        registerRequest = UserRegisterRequestDto.builder()
                .username("username1")
                .email("test1@example.com")
                .password(password)
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
                .refreshToken(refreshToken)
                .build();

        refreshTokenDto = RefreshTokenDto.builder()
                .id(1234L)
                .token(refreshToken)
                .userId(userId)
                .build();

        refreshTokenEntity = RefreshToken.builder()
                .id(123L)
                .authUser(authUser)
                .token(refreshToken)
                .build();
    }

    @AfterEach
    void cleanUp() {
        userCreateResponse = null;
        userRole = null;
        registerRequest = null;
        registerResponse = null;
        refreshTokenDto = null;
        refreshTokenEntity = null;
    }

    @Test
    void register_shouldRegister_whenUserDataIsCorrect() {
        when(passwordEncoder.encode(anyString())).thenReturn(password);
        when(userClient.create(any(UserServiceCreateRequestDto.class)))
                .thenReturn(new ResponseEntity<>(userCreateResponse, HttpStatus.CREATED));
        when(authUserRepository.save(any(AuthUser.class))).thenReturn(authUser);
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));

        UserRegisterResponseDto actualResponse = authService.register(registerRequest);

        assertThat(actualResponse).isEqualTo(registerResponse);
    }

    @Test
    void register_clientShouldThrow_whenUserDataIsIncorrect() {
        when(passwordEncoder.encode(anyString())).thenReturn(password);
        when(userClient.create(any(UserServiceCreateRequestDto.class)))
                .thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () ->
                authService.register(registerRequest));
    }

    @Test
    void register_shouldThrow_whenRoleIsNotFound() {
        when(passwordEncoder.encode(anyString())).thenReturn(password);
        when(userClient.create(any(UserServiceCreateRequestDto.class)))
                .thenReturn(new ResponseEntity<>(userCreateResponse, HttpStatus.CREATED));
        when(roleRepository.findByName(any(Role.RoleName.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(RoleNotFoundException.class, () ->
                authService.register(registerRequest));
    }

    @Test
    void login_shouldLogin_ifLoginDataCorrect() {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("username", "pass", authorities);

        when(authUserRepository.findByUsername(anyString())).thenReturn(Optional.of(authUser));
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(token);
        when(jwtService.generate(any(UserDetails.class))).thenReturn(jwtToken);
        when(refreshTokenService.generateRefreshToken(anyString())).thenReturn(refreshTokenDto);

        LoginJwtResponseDto actualResponse = authService.login(loginRequest);

        assertThat(actualResponse).isEqualTo(loginResponse);
        assertThat(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()).isTrue();
    }

    @Test
    void login_shouldThrow_ifUserWithSuchUsernameDoesNotExist() {
        when(authUserRepository.findByUsername(anyString())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> authService.login(loginRequest));
    }

    @Test
    void login_shouldThrow_ifPasswordIncorrect() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("username", "pass");
        when(authUserRepository.findByUsername(anyString())).thenReturn(Optional.of(authUser));
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(token);

        assertThrows(IncorrectPasswordException.class, () -> authService.login(loginRequest));
    }

    @Test
    void refresh_shouldReturnJwtTokens_ifRefreshTokenValid() {
        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(refreshTokenEntity));
        when(refreshTokenService.generateRefreshToken(anyString())).thenReturn(refreshTokenDto);
        when(jwtService.generate(any(UserDetails.class))).thenReturn(jwtToken);

        LoginJwtResponseDto actualResponse = authService.refresh(refreshToken);

        assertThat(actualResponse).isEqualTo(loginResponse);
    }

    @Test
    void refresh_shouldThrow_ifRefreshTokenInvalid() {
        when(refreshTokenService.generateRefreshToken(anyString())).thenThrow(InvalidRefreshTokenException.class);

        assertThrows(InvalidRefreshTokenException.class, () ->
                authService.refresh(refreshToken));
    }
}