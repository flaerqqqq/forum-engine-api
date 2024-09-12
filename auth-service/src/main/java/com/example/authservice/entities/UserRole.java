package com.example.authservice.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing the association between a user and a role.
 * <p>
 * This entity defines the mapping between {@link AuthUser} and {@link Role}. It represents the roles assigned
 * to users in the system, allowing for user-role relationships.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user_roles")
public class UserRole {

    /**
     * The unique identifier of the user-role mapping.
     * <p>
     * This ID is used to uniquely identify a user-role mapping within the system. It is a primary key in the database.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user associated with this role mapping.
     * <p>
     * This is a many-to-one relationship with the {@link AuthUser} entity, representing the user to whom the role is assigned.
     * The foreign key is defined in the {@code user_id} column.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser authUser;

    /**
     * The role associated with this user-role mapping.
     * <p>
     * This is a many-to-one relationship with the {@link Role} entity, representing the role assigned to the user.
     * The foreign key is defined in the {@code role_id} column.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
