package com.example.authservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "auth_users")
public class AuthUser {

    @Id
    private String id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "authUser", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;
}
