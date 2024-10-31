package com.example.authservice.tasks;

import com.example.authservice.entities.RefreshToken;
import com.example.authservice.repositories.RefreshTokenRepository;
import com.example.authservice.services.JwtService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A scheduled task component for cleaning up expired JWT refresh tokens from the database.
 * <p>
 * This component periodically scans the database for refresh tokens and deletes those that
 * have expired. The cleaning task runs at a fixed rate defined by the {@code fixedRate} property
 * in the {@code @Scheduled} annotation.
 * </p>
 */
@Component
@AllArgsConstructor
public class ExpiredRefreshTokenCleaningTask {

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Scheduled method to clean up expired refresh tokens.
     * <p>
     * This method runs every hour, fetching tokens in batches, checking their validity using
     * {@link JwtService#isValid(String)}, and deleting the expired tokens from the database.
     * </p>
     * <p>
     * The process involves:
     * <ul>
     *     <li>Fetching tokens in batches of a defined size.</li>
     *     <li>Filtering tokens to find those that are expired.</li>
     *     <li>Deleting expired tokens in bulk.</li>
     *     <li>Continuing the process until all tokens are processed.</li>
     * </ul>
     * </p>
     */
    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void cleanUpExpiredTokens() {
        int batchSize = 10000;
        int page = 0;
        boolean hasMoreTokens = true;

        while(hasMoreTokens) {
            Page<RefreshToken> tokenPage = refreshTokenRepository.findAll(PageRequest.of(page++, batchSize));
            hasMoreTokens = !tokenPage.isEmpty();

            List<RefreshToken> expiredTokens = tokenPage.getContent().stream()
                    .filter((token) -> jwtService.isValid(token.getToken()))
                    .toList();

            if (!expiredTokens.isEmpty()) {
                refreshTokenRepository.deleteAll(expiredTokens);
            }
        }
    }
}
