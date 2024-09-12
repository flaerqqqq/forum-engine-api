package com.example.authservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entity representing a role in the system.
 * <p>
 * This entity defines various roles that can be assigned to users. Each role has a unique identifier
 * and a name, and it can be associated with multiple user-role mappings.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    /**
     * The unique identifier of the role.
     * <p>
     * This ID is used to uniquely identify a role within the system. It is a primary key in the database.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the role.
     * <p>
     * This is an enumeration that defines the name of the role, represented as a string.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    private RoleName name;

    /**
     * The user-role mappings associated with this role.
     * <p>
     * This is a list of {@link UserRole} entities representing the mappings between this role and users.
     * The relationship is mapped by the {@code role} field in the {@link UserRole} entity.
     * </p>
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    /**
     * Enum representing the possible names of roles.
     */
    public enum RoleName {
        ROLE_USER
    }
}
