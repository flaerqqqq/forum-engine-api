package com.example.authservice.repositories;

import com.example.authservice.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for managing {@link RefreshToken} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing methods for standard CRUD operations
 * and custom queries related to {@link RefreshToken} entities. It allows querying, saving, updating,
 * and deleting refresh tokens in the underlying database. Additionally, it includes a custom method
 * to find a refresh token based on the associated user's ID.
 * </p>
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Finds a {@link RefreshToken} by the associated user's ID.
     * <p>
     * This method retrieves the refresh token for a specific user by querying the {@code authUser}'s ID.
     * It returns an {@link Optional} containing the {@link RefreshToken} if found, or an empty Optional if not.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return an {@link Optional} containing the {@link RefreshToken} if found, otherwise an empty Optional
     */
    @Query("SELECT rt FROM RefreshToken rt WHERE rt.authUser.id = :userId")
    Optional<RefreshToken> findByUserId(String userId);
}
