package com.example.user_service.repositories;

import com.example.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link User} entities.
 * <p>
 * This interface extends {@link MongoRepository} and provides methods for querying users based on their username and email.
 * </p>
 */
public interface UserRepository extends MongoRepository<User, String>{

    /**
     * Checks if a user with the given username exists in the repository.
     *
     * @param username the username to check
     * @return {@code true} if a user with the given username exists, {@code false} otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user with the given email exists in the repository.
     *
     * @param email the email to check
     * @return {@code true} if a user with the given email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the {@link User} if found, or {@link Optional#empty()} if not found
     */
    Optional<User> findByUsername(String username);
}
