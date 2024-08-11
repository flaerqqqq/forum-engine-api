package com.example.authservice.repositories;

import com.example.authservice.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, String> {
    Optional<AuthUser> findByUsername(String username);
}
