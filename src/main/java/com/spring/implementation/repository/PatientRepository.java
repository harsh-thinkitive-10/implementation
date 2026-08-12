package com.spring.implementation.repository;

import com.spring.implementation.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,Long> {

    Optional<PatientEntity> findByKeycloakUserId(String keycloakUserId);

}
