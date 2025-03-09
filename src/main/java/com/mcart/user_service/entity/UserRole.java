package com.mcart.user_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mcart.user_service.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_role_id", updatable = false, nullable = false)
    private UUID userRoleId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference  // Marks this as the "child" reference
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role; // Enum Role
}
