package com.spring.implementation.repository;

import com.spring.implementation.dto.projection.DoctorDashboardView;
import com.spring.implementation.entity.DoctorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<DoctorEntity,Long> {

    @Query(value = "SELECT * FROM doctor",nativeQuery = true)
    List<DoctorEntity> findAllDoctors();

    Optional<DoctorEntity> findByKeycloakUserId(String keycloakUserId);

    @Query(value = """
        SELECT
            COUNT(a.appointment_id) AS totalAppointments,

            COUNT(
                CASE
                    WHEN a.status = 'SCHEDULED'
                    THEN 1
                END
            ) AS scheduledAppointments,

            COUNT(
                CASE
                    WHEN a.status = 'COMPLETED'
                    THEN 1
                END
            ) AS completedAppointments,

            COUNT(
                CASE
                    WHEN a.status = 'CANCELLED'
                    THEN 1
                END
            ) AS cancelledAppointments,

            COUNT(DISTINCT a.patient_id) AS totalPatients

        FROM doctor d

        LEFT JOIN appointment a
            ON a.doctor_id = d.doctor_id

        WHERE d.keycloak_user_id = :keycloakUserId
        """,
            nativeQuery = true)
    DoctorDashboardView getDoctorDashboard(
            @Param("keycloakUserId") String keycloakUserId
    );

    Optional<DoctorEntity> findByUuid(UUID uuid);

    Page<DoctorEntity> findAll(Pageable pageable);

    @Query("""
        SELECT d
        FROM DoctorEntity d
        WHERE
            (:isActive IS NULL OR d.isActive = :isActive)
        AND (
            :search IS NULL
            OR :search = ''
            OR LOWER(d.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(d.email) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :search, '%'))
            OR d.phoneNumber LIKE CONCAT('%', :search, '%')
        )
        AND (
            :specialization IS NULL
            OR :specialization = ''
            OR LOWER(d.specialization) = LOWER(:specialization)
        )
        """)
    Page<DoctorEntity> findDoctors(
            @Param("search") String search,
            @Param("specialization") String specialization,
            @Param("isActive") Boolean isActive,
            Pageable pageable
    );

    Optional<DoctorEntity> findByUuidAndIsActiveTrue(UUID uuid);

}
