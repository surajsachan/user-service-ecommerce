package com.mcart.user_service.controller;

import com.mcart.user_service.entity.User;
import com.mcart.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/getUser/{identifier}")
    public ResponseEntity<?> getUserProfile(@PathVariable String identifier) {
        try {
            User user = userService.getUserByIdentifier(identifier);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/updateUser/{identifier}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String identifier, @RequestBody User updatedUser) {
        try {
            User updatedUserProfile = userService.updateUserProfile(identifier, updatedUser);
            return ResponseEntity.ok(updatedUserProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/storeProfile")
    public ResponseEntity<?> storeUserProfile(@RequestBody User user) {
        try {
            User savedUser = userService.saveCognitoUser(
                    user.getCognitoUserId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone()
            );
            return ResponseEntity.ok("User profile saved with ID: " + savedUser.getUserId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}