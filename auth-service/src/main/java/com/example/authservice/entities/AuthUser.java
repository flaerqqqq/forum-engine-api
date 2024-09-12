package com.example.authservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing an authenticated user in the system.
 * <p>
 * This entity stores information about users who have registered in the system,
 * including their unique identifier, username, password, and associated roles.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "auth_users")
public class AuthUser {

    /**
     * The unique identifier of the user.
     * <p>
     * This ID is used to uniquely identify a user within the system. It is a primary key in the database.
     * </p>
     */
    @Id
    private String id;

    /**
     * The username of the user.
     * <p>
     * This is the unique username chosen by the user during registration.
     * </p>
     */
    private String username;

    /**
     * The password of the user.
     * <p>
     * This is the password chosen by the user, which is stored in an encrypted form.
     * </p>
     */
    private String password;

    /**
     * The roles associated with the user.
     * <p>
     * This is a list of {@link UserRole} entities representing the roles assigned to the user.
     * The relationship is mapped by the {@code authUser} field in the {@link UserRole} entity.
     * </p>
     */
    @OneToMany(mappedBy = "authUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> userRoles = new ArrayList<>();
}
