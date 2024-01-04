package org.psota.taskmanagementbe.persistance.dao;

import org.psota.taskmanagementbe.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    Optional<User> findById(UUID uuid);
    Optional<User> findByUsername(String username);
}
