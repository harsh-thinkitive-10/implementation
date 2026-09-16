package com.spring.implementation.repository;

import com.spring.implementation.entity.AppointmentEntity;
import com.spring.implementation.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity,Long>{

    boolean existsByAppointment(AppointmentEntity appointment);

    Optional<PrescriptionEntity> findByUuid(UUID uuid);

    Optional<PrescriptionEntity> findByAppointmentUuid(UUID appointmentUuid);

}
