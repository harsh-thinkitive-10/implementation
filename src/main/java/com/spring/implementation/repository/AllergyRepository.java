package com.spring.implementation.repository;

import com.spring.implementation.dto.projection.AllergyView;
import com.spring.implementation.entity.AllergyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AllergyRepository extends JpaRepository<AllergyEntity, Long> {

    @Query(value = """
            SELECT
                a.uuid AS uuid,
                a.patient_id AS patientId,
                a.name AS name,
                a.allergen AS allergen,
                a.reaction AS reaction,
                a.severity AS severity,
                a.onset_date AS onsetDate,
                a.source AS source,
                a.status AS status,
                a.notes AS notes
            FROM allergy a
            WHERE a.patient_id = :patientId
            ORDER BY a.onset_date DESC
            """, countQuery = """
            SELECT COUNT(*)
            FROM allergy a
            WHERE a.patient_id = :patientId
            """, nativeQuery = true)
    Page<AllergyView> findByPatientId(@Param("patientId") Long patientId, Pageable pageable);

    @Query(value = """
            SELECT
                a.uuid AS uuid,
                a.patient_id AS patientId,
                a.name AS name,
                a.allergen AS allergen,
                a.reaction AS reaction,
                a.severity AS severity,
                a.onset_date AS onsetDate,
                a.source AS source,
                a.status AS status,
                a.notes AS notes
            FROM allergy a
            WHERE a.uuid = :uuid
            """, nativeQuery = true)
    Optional<AllergyView> findViewByUuid(@Param("uuid") UUID uuid);

    Optional<AllergyEntity> findByUuid(UUID uuid);

    @Modifying
    @Query(value = """
            DELETE FROM allergy
            WHERE uuid = :uuid
            """, nativeQuery = true)
    int deleteByUuid(@Param("uuid") UUID uuid);
}