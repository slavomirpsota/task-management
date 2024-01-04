package org.psota.taskmanagementbe.persistance.dao;

import org.psota.taskmanagementbe.persistance.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;

public interface RoleDao extends JpaRepository<Role, UUID>, QuerydslPredicateExecutor<Role> {
    public Optional<Role> getRoleByRoleName(String roleName);

}
