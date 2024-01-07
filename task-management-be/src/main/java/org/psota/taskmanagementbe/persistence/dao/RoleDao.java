package org.psota.taskmanagementbe.persistence.dao;

import org.psota.taskmanagementbe.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface RoleDao extends JpaRepository<Role, UUID>, QuerydslPredicateExecutor<Role> {
    public Optional<Role> getRoleByRoleName(String roleName);

}
