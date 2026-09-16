package com.spring.implementation.repository;

import com.spring.implementation.dto.projection.AppointmentAdminView;
import com.spring.implementation.dto.projection.AppointmentDoctorView;
import com.spring.implementation.dto.projection.AppointmentPatientView;
import com.spring.implementation.entity.AppointmentEntity;
import com.spring.implementation.entity.PrescriptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {

    @Query(
            value = """
                    SELECT
                        a.appointment_date AS appointmentDate,
                        a.reason_for_visit AS reasonForVisit,
                        a.status AS status,

                        p.full_name AS patientFullName,
                        p.age AS patientAge,
                        p.gender AS patientGender,
                        p.phone_number AS patientPhoneNumber,
                        p.email AS patientEmail,

                        d.full_name AS doctorFullName,
                        d.specialization AS doctorSpecialization,
                        d.phone_number AS doctorPhoneNumber,
                        d.email AS doctorEmail,
                        d.consultation_fee AS consultationFee

                    FROM appointment a

                    LEFT JOIN patient p
                        ON a.patient_id = p.patient_id

                    LEFT JOIN doctor d
                        ON a.doctor_id = d.doctor_id
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM appointment a
                    """,
            nativeQuery = true
    )
    Page<AppointmentAdminView> findAllAppointments(Pageable pageable);

    @Query(
            value = """
                    SELECT
                        a.appointment_date AS appointmentDate,
                        a.reason_for_visit AS reasonForVisit,
                        a.status AS status,

                        d.full_name AS doctorFullName,
                        d.specialization AS doctorSpecialization,
                        d.phone_number AS doctorPhoneNumber,
                        d.email AS doctorEmail,
                        d.consultation_fee AS consultationFee

                    FROM appointment a

                    INNER JOIN patient p
                        ON a.patient_id = p.patient_id

                    LEFT JOIN doctor d
                        ON a.doctor_id = d.doctor_id

                    WHERE p.keycloak_user_id = :keycloakUserId
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM appointment a

                    INNER JOIN patient p
                        ON a.patient_id = p.patient_id

                    WHERE p.keycloak_user_id = :keycloakUserId
                    """,
            nativeQuery = true
    )
    Page<AppointmentPatientView> findAppointmentsForPatientByUserId(
            @Param("keycloakUserId") String keycloakUserId,
            Pageable pageable
    );

    @Query(
            value = """
                    SELECT
                        a.appointment_date AS appointmentDate,
                        a.reason_for_visit AS reasonForVisit,
                        a.status AS status,

                        p.full_name AS patientFullName,
                        p.age AS patientAge,
                        p.gender AS patientGender,
                        p.phone_number AS patientPhoneNumber,
                        p.email AS patientEmail

                    FROM appointment a

                    INNER JOIN doctor d
                        ON a.doctor_id = d.doctor_id

                    LEFT JOIN patient p
                        ON a.patient_id = p.patient_id

                    WHERE d.keycloak_user_id = :keycloakUserId
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM appointment a

                    INNER JOIN doctor d
                        ON a.doctor_id = d.doctor_id

                    WHERE d.keycloak_user_id = :keycloakUserId
                    """,
            nativeQuery = true
    )
    Page<AppointmentDoctorView> findAppointmentsForDoctorByUserId(
            @Param("keycloakUserId") String keycloakUserId,
            Pageable pageable
    );

    Optional<AppointmentEntity> findByUuid(UUID uuid);

}
