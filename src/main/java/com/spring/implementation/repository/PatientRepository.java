package com.spring.implementation.repository;

import com.spring.implementation.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,Long> {

    Optional<PatientEntity> findByKeycloakUserId(String keycloakUserId);

    @Query(value = """
        SELECT p.keycloak_user_id
        FROM patient p
        WHERE p.patient_id = :id
       """,nativeQuery = true)
    String findKeycloakUserIdByPatientId(@Param("id") Long id);

}
