package com.example.authservice.repositories;

import com.example.authservice.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("SELECT ur FROM UserRole ur JOIN ur.authUser au WHERE au.id = :userId")
    List<UserRole> findAllByUserId(@Param("userId") String userId);
}
