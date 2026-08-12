package com.spring.implementation.repository;

import com.spring.implementation.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity,Long>{
}
