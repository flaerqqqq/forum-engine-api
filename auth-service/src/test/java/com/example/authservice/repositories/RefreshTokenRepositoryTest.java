package com.example.authservice.repositories;

import com.example.authservice.config.PostgreSQLTestContainerInitializer;
import com.example.authservice.entities.AuthUser;
import com.example.authservice.entities.RefreshToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RefreshTokenRepositoryTest extends PostgreSQLTestContainerInitializer {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    final String userId = "1234";
    final String refreshTokenValue = "refreshTokenValue";

    @BeforeEach
    void initialization() {
        AuthUser authUser = AuthUser.builder()
                .id(userId)
                .username("username")
                .password("password")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshTokenValue)
                .authUser(authUser)
                .build();

        authUserRepository.save(authUser);
        refreshTokenRepository.save(refreshToken);
    }

    @AfterEach
    void cleanUp() {
        authUserRepository.deleteAll();
        refreshTokenRepository.deleteAll();
    }

    @Test
    void findByUserId_shouldReturnRefreshToken_ifUserExists() {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(userId);

        assertThat(refreshToken).isPresent();
    }

    @Test
    void findByUserId_shouldReturnNull_ifTokenWithSuchUserDoesNotExist() {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId("some-user-id");

        assertThat(refreshToken).isNotPresent();
    }

    @Test
    void findByToken_shouldReturnRefreshToken_ifTokenExistsInDB() {
        Optional<RefreshToken> foundToken = refreshTokenRepository.findByToken(refreshTokenValue);

        assertThat(foundToken).isPresent();
        assertThat(foundToken.get().getToken()).isEqualTo(refreshTokenValue);
    }

    @Test
    void findByToken_shouldReturnNull_ifTokenDoesNotExistInDB() {
        Optional<RefreshToken> foundToken = refreshTokenRepository.findByToken("somethingDifferent");

        assertThat(foundToken).isEmpty();
    }
}
