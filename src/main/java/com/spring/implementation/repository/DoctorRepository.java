package com.spring.implementation.repository;

import com.spring.implementation.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity,Long> {

    @Query(value = "SELECT * FROM doctor",nativeQuery = true)
    List<DoctorEntity> findAllDoctors();

    @Query(value = "SELECT * FROM doctor WHERE doctor_id = :id",nativeQuery = true)
    DoctorEntity findDoctorById(Long id);

    @Query(value = "UPDATE doctor SET consultation_fee=: fees WHERE doctor_id = :id",nativeQuery = true)
    void updateDoctorConsultationFee(Long id,Double fees);

    @Query(value = "DELETE FROM doctor WHERE doctor_id = :id",nativeQuery = true)
    void deleteDoctorById(Long id);

    Optional<DoctorEntity> findByKeycloakUserId(String keycloakUserId);

}
