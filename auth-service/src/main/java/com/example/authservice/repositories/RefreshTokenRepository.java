package com.example.authservice.repositories;

import com.example.authservice.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link RefreshToken} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing methods for standard CRUD operations
 * and database interactions with the {@link RefreshToken} entity. It enables querying, saving, updating,
 * and deleting refresh tokens in the underlying database.
 * </p>
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
