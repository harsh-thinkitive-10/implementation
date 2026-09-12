package com.spring.implementation.repository;

import com.spring.implementation.dto.projection.AppointmentView;
import com.spring.implementation.entity.AppointmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {

    @Query(value = """
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
            LEFT JOIN patient p on a.patient_id= p.patient_id
            LEFT JOIN doctor d on d.doctor_id = a.doctor_id;
                    """,nativeQuery = true)
    Page<AppointmentView> findAllAppointment(Pageable pageable);

    @Query(value = """
                    SELECT 
     """,
         nativeQuery = true
    )
    Page<AppointmentView> findPatientAppointment(Pageable pageable);

    @Query(value = """
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
                    LEFT JOIN patient p on a.patient_id= p.patient_id
                    LEFT JOIN doctor d on d.doctor_id = a.doctor_id
                    WHERE a.patient_id = :id;
                    """,nativeQuery = true)
    List<AppointmentView> findAppointmentForPatientByPatientId(@Param("id") Long id);

    @Query(value = """
                    SELECT
                    
                    a.appointment_date AS appointmentDate,
                    a.appointment_time AS appointmentTime,
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
                    LEFT JOIN patient p on a.patient_id= p.patient_id
                    LEFT JOIN doctor d on d.doctor_id = a.doctor_id
                    WHERE d.doctor_id = :id;
                    """,nativeQuery = true)
    List<AppointmentView> findAppointmentForDoctorById(@Param("id") Long id);

    @Query(value = """
        SELECT
            a.appointment_date AS appointmentDate,
            a.appointment_time AS appointmentTime,
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

        WHERE p.keycloak_user_id = :keycloakUserId
        """, nativeQuery = true)
    List<AppointmentView> findAppointmentsForPatientByUserId(
            @Param("keycloakUserId") String UserId
    );

}
