package com.example.authservice.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a refresh token stored in the database.
 * <p>
 * This entity maps to the "refresh_tokens" table in the database and stores the refresh token details,
 * including the token value, its unique identifier, and the associated user. The refresh token allows the
 * client to obtain a new JWT token without re-authenticating.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    /**
     * The unique identifier of the refresh token.
     * <p>
     * This ID is automatically generated and used to uniquely identify the refresh token in the database.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The refresh token value.
     * <p>
     * This token is a unique string used to obtain a new JWT token when the current one expires.
     * </p>
     */
    @Column(nullable = false, unique = true)
    private String token;

    /**
     * The user associated with the refresh token.
     * <p>
     * This field represents the one-to-one relationship between the refresh token and the user (AuthUser)
     * who is authorized to use it to obtain a new JWT token.
     * </p>
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private AuthUser authUser;
}
