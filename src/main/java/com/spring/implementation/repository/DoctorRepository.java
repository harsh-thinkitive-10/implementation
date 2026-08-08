package com.spring.implementation.repository;

import com.spring.implementation.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Query(value = "SELECT * FROM doctor",nativeQuery = true)
    List<Doctor> findAllDoctors();

    @Query(value = "SELECT * FROM doctor WHERE doctor_id = :id",nativeQuery = true)
    Doctor findDoctorById(Long id);

    @Query(value = "UPDATE doctor SET consultation_fee=: fees WHERE doctor_id = :id",nativeQuery = true)
    void updateDoctorConsultationFee(Long id,Double fees);

    @Query(value = "DELETE FROM doctor WHERE doctor_id = :id",nativeQuery = true)
    void deleteDoctorById(Long id);

}
