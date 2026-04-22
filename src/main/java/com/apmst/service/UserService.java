package com.apmst.service;

import java.util.List;
import java.util.Optional;

import com.apmst.entity.User;

public interface UserService {

	 // Register new user
    User saveUser(User user);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Find user by id
    Optional<User> findById(Long id);

    // Get all users
    List<User> getAllUsers();

    // Update user
    User updateUser(User user);

    // Delete user
    void deleteUser(Long id);

    // Check if email exists
    boolean existsByEmail(String email);

    // Update user points
    void updatePoints(Long userId, int points);
}
