package com.spring.implementation.repository;

import com.spring.implementation.dto.projection.PatientDashboardProjection;
import com.spring.implementation.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    Optional<PatientEntity> findByKeycloakUserId(String keycloakUserId);

    @Query(value = """
        SELECT p.keycloak_user_id
        FROM patient p
        WHERE p.patient_id = :id
        """,
            nativeQuery = true)
    String findKeycloakUserIdByPatientId(
            @Param("id") Long id
    );

    @Query(value = """
        SELECT
            p.full_name AS fullName,

            COUNT(
                CASE
                    WHEN a.status = 'UPCOMING'
                    THEN 1
                END
            ) AS upcomingAppointments,

            COUNT(
                CASE
                    WHEN a.status = 'COMPLETED'
                    THEN 1
                END
            ) AS completedAppointments,

            COUNT(
                CASE
                    WHEN pr.prescription_id IS NOT NULL
                    THEN 1
                END
            ) AS activePrescriptions

        FROM patient p

        LEFT JOIN appointment a
            ON a.patient_id = p.patient_id

        LEFT JOIN prescription pr
            ON pr.appointment_id = a.appointment_id

        WHERE p.keycloak_user_id = :keycloakUserId

        GROUP BY p.patient_id, p.full_name
        """,
            nativeQuery = true)
    PatientDashboardProjection getPatientDashboard(
            @Param("keycloakUserId") String keycloakUserId
    );

    Optional<PatientEntity> findByUuid(UUID uuid);

    /**
     * Fetches the unique patients who have appointments
     * with the specified doctor.
     *
     * Pagination and sorting are handled through Pageable.
     */
    @Query("""
        SELECT DISTINCT p
        FROM PatientEntity p
        JOIN AppointmentEntity a
            ON a.patient = p
        WHERE a.doctor.doctorId = :doctorId
        ORDER BY p.fullName ASC
        """)
    Page<PatientEntity> findPatientsByDoctorId(
            @Param("doctorId") Long doctorId,
            Pageable pageable
    );
}