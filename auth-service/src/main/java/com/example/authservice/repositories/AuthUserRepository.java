package com.example.authservice.repositories;

import com.example.authservice.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link AuthUser} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods for {@link AuthUser} entities.
 * </p>
 */
public interface AuthUserRepository extends JpaRepository<AuthUser, String> {

    /**
     * Finds an {@link AuthUser} by their username.
     * <p>
     * This method provides a way to retrieve a user entity based on its username.
     * </p>
     *
     * @param username the username of the user to retrieve
     * @return an {@link Optional} containing the {@link AuthUser} if found, otherwise {@link Optional#empty()}
     */
    Optional<AuthUser> findByUsername(String username);
}
