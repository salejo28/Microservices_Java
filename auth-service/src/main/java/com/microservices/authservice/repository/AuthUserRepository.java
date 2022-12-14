package com.microservices.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.authservice.entity.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
  Optional<AuthUser> findByUsername(String username);
}
