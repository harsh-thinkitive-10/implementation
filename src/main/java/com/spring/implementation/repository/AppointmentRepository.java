package com.spring.implementation.repository;

import com.spring.implementation.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

//    @Query(value = """
//            Select * from appointment a
//                        where a.patient_id = :id
//            """, nativeQuery = true)

        @Query(value = """
            Select a from Appointment a
                        where a.patient.patientId = :id
            """)
    List<Appointment> findAppointmentByPatientPatientId(Long id);
}
