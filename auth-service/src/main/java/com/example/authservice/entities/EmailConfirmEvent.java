package com.example.authservice.entities;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Represents an email confirmation event.
 * <p>
 * This class encapsulates the details of an email confirmation event, including
 * the user's unique identifier (UUID) and their email address. It uses Lombok annotations
 * to generate boilerplate code such as getters, setters, and a builder for easier object creation.
 * </p>
 */
@Data
@ToString
@Builder
public class EmailConfirmEvent {

    /**
     * The unique identifier of the user associated with the email confirmation.
     */
    private String uuid;

    /**
     * The email address to which the confirmation is sent.
     */
    private String email;
}
