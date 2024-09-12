package com.example.authservice.repositories;

import com.example.authservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Role} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods for {@link Role} entities.
 * </p>
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a {@link Role} by its name.
     * <p>
     * This method provides a way to retrieve a role entity based on its name.
     * </p>
     *
     * @param roleName the name of the role to retrieve, encapsulated in {@link Role.RoleName}
     * @return an {@link Optional} containing the {@link Role} if found, otherwise {@link Optional#empty()}
     */
    Optional<Role> findByName(Role.RoleName roleName);
}
