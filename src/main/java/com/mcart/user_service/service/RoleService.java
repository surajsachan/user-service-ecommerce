package com.mcart.user_service.service;

import com.mcart.user_service.entity.User;
import com.mcart.user_service.entity.UserRole;
import com.mcart.user_service.enums.Role;
import com.mcart.user_service.repository.UserRepository;
import com.mcart.user_service.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    // ✅ Fetch user roles using cognitoUserId or userId
    public List<Role> getUserRoles(String identifier) {
        Optional<User> userOptional = userRepository.findByCognitoUserId(identifier);
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findById(UUID.fromString(identifier));
        }

        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found with identifier: " + identifier));
        return userRoleRepository.findByUser(user)
                .stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }

    // ✅ Assign a role to a user
    public String assignRole(String cognitoUserId, Role newRole) {
        User user = userRepository.findByCognitoUserId(cognitoUserId)
                .orElseThrow(() -> new RuntimeException("User not found with Cognito ID: " + cognitoUserId));

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(newRole)
                .build();

        userRoleRepository.save(userRole);
        return "Role " + newRole + " assigned to user " + user.getEmail();
    }

    // ✅ Get all users with a specific role
    public List<User> getUsersByRole(Role role) {
        return userRoleRepository.findByRole(role)
                .stream()
                .map(UserRole::getUser)
                .collect(Collectors.toList());
    }
}
