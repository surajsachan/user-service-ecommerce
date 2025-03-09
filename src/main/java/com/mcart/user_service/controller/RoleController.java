package com.mcart.user_service.controller;

import com.mcart.user_service.enums.Role;
import com.mcart.user_service.service.RoleService;
import com.mcart.user_service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // ✅ API to fetch a user's roles by CognitoUserId or UserId
    @GetMapping("/getUserRole/{identifier}")
    public ResponseEntity<?> getUserRoles(@PathVariable String identifier) {
        try {
            List<Role> roles = roleService.getUserRoles(identifier);
            return ResponseEntity.ok(roles);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ API to assign a role to a user
    @PostMapping("/assignRole")
    public ResponseEntity<?> assignRole(@RequestParam String cognitoUserId, @RequestParam Role role) {
        try {
            String response = roleService.assignRole(cognitoUserId, role);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ API to get all users by role
    @GetMapping("/users/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Role role) {
        List<User> users = roleService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
}
