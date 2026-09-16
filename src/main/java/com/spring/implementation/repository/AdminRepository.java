package com.spring.implementation.repository;

import com.spring.implementation.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    Optional<AdminEntity> findByKeycloakUserId(String keycloakUserId);

    Optional<AdminEntity> findByUuid(UUID uuid);

    boolean existsByKeycloakUserId(String keycloakUserId);

    boolean existsByEmail(String email);
}