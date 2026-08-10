package com.spring.implementation.repository;

import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.projection.AppointmentView;
import com.spring.implementation.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query(value = """
            Select * from appointment a
                        where a.patient_id = :id
            """, nativeQuery = true)
    List<Appointment> findAppointmentByPatientPatientId(Long id);

    @Query(value = """
                    SELECT
                    p.full_name AS patientName,
                    d.full_name AS doctorName,
                    a.appointment_date AS appointmentDate
                                            
                    FROM appointment a
                    LEFT JOIN patient p on a.patient_id= p.patient_id
                    LEFT JOIN doctor d on d.doctor_id = a.doctor_id
                    WHERE a.patient_id = :id;
                    """,nativeQuery = true)
    List<AppointmentView> findAppointmentForPatientByPatientId(Long id);

}
