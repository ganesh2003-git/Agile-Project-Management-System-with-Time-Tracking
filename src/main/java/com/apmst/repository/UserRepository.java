package com.apmst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apmst.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

//	 // Find user by email (used during login)
    Optional<User> findByEmail(String email);
//
//    // Check if email already exists (used during registration)
    boolean existsByEmail(String email);
}
