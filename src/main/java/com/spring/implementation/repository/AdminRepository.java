package com.spring.implementation.repository;

import com.spring.implementation.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    Optional<AdminEntity> findByKeycloakUserId(String keycloakUserId);

    Optional<AdminEntity> findByUuid(UUID uuid);

    boolean existsByKeycloakUserId(String keycloakUserId);

    boolean existsByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM patient WHERE is_active = 1", nativeQuery = true)
    long countPatients();

    @Query(value = "SELECT COUNT(*) FROM doctor WHERE is_active = 1", nativeQuery = true)
    long countDoctors();

    @Query(value = "SELECT COUNT(*) FROM location WHERE is_active = 1", nativeQuery = true)
    long countLocations();

    @Query(value = "SELECT COUNT(*) FROM appointment", nativeQuery = true)
    long countAppointments();

    @Query(value = """
            SELECT COUNT(*)
            FROM appointment
            WHERE DATE(appointment_date) = CURRENT_DATE
            """, nativeQuery = true)
    long countTodayAppointments();

    @Query(value = """
            SELECT COUNT(*)
            FROM appointment
            WHERE status = 'SCHEDULED'
            """, nativeQuery = true)
    long countScheduledAppointments();

    @Query(value = """
            SELECT COUNT(*)
            FROM appointment
            WHERE status = 'COMPLETED'
            """, nativeQuery = true)
    long countCompletedAppointments();

    @Query(value = """
            SELECT COUNT(*)
            FROM appointment
            WHERE status = 'CANCELLED'
            """, nativeQuery = true)
    long countCancelledAppointments();
}