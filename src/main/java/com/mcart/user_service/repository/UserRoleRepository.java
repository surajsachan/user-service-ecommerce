package com.mcart.user_service.repository;

import com.mcart.user_service.entity.UserRole;
import com.mcart.user_service.entity.User;
import com.mcart.user_service.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    List<UserRole> findByUser(User user);
    List<UserRole> findByRole(Role role);
}
