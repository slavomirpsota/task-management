package org.psota.taskmanagementbe.persistence.dao;

import org.psota.taskmanagementbe.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface UserDao extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    Optional<User> findById(UUID uuid);
    Optional<User> findByUsername(String username);
}
