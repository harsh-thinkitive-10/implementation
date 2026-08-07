package com.spring.implementation.repository;

import com.spring.implementation.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long>{
}
