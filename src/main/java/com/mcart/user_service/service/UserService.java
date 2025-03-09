package com.mcart.user_service.service;

import com.mcart.user_service.entity.User;
import com.mcart.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    // Fetch user profile by ID
    public User getUserByIdentifier(String identifier) {
        // Try finding user by Cognito ID first, if not found then try Email
        Optional<User> userOptional = userRepository.findByCognitoUserId(identifier);
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByEmail(identifier);
        }

        return userOptional.orElseThrow(() -> new RuntimeException("User not found with Cognito ID or Email: " + identifier));
    }

    public User updateUserProfile(String identifier, User updatedUser) {
        if (updatedUser == null) {
            throw new IllegalArgumentException("Updated user data cannot be null");
        }

        // Try to find user by Cognito ID first, if not found then try Email
        Optional<User> userOptional = userRepository.findByCognitoUserId(identifier);
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByEmail(identifier);
        }

        return userOptional.map(existingUser -> {
            // Only update fields that are not null
            if (updatedUser.getName() != null) {
                existingUser.setName(updatedUser.getName());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPhone() != null) {
                existingUser.setPhone(updatedUser.getPhone());
            }

            return userRepository.save(existingUser); // Save and return updated user
        }).orElseThrow(() -> new RuntimeException("User not found with Cognito ID or Email: " + identifier));
    }

    public User saveCognitoUser(String cognitoUserId, String name, String email, String phone) {
        // Check if user already exists
        Optional<User> existingUser = userRepository.findByCognitoUserId(cognitoUserId);
        if (existingUser.isPresent()) {
            return existingUser.get(); // Return existing user
        }

        // Create new user profile
        User newUser = new User();
        newUser.setCognitoUserId(cognitoUserId);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);

        return userRepository.save(newUser);
    }
}
