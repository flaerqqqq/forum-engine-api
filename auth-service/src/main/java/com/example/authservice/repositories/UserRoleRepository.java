package com.example.authservice.repositories;

import com.example.authservice.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for managing {@link UserRole} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods for {@link UserRole} entities.
 * </p>
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    /**
     * Finds all {@link UserRole} entities associated with a specific user.
     * <p>
     * This method performs a custom query to retrieve all user roles linked to the given user ID.
     * </p>
     *
     * @param userId the ID of the user whose roles are to be retrieved
     * @return a {@link List} of {@link UserRole} entities associated with the specified user ID
     */
    @Query("SELECT ur FROM UserRole ur JOIN ur.authUser au WHERE au.id = :userId")
    List<UserRole> findAllByUserId(@Param("userId") String userId);
}
