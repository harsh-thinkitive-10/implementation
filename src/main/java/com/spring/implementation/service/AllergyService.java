package com.spring.implementation.service;

import com.spring.implementation.dto.AllergyRequestDTO;
import com.spring.implementation.dto.AllergyResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AllergyService {

    AllergyResponseDTO create(AllergyRequestDTO request);

    Page<AllergyResponseDTO> getByPatient(UUID patientUuid, Pageable pageable);

    AllergyResponseDTO getByUuid(UUID uuid);

    AllergyResponseDTO update(UUID uuid, AllergyRequestDTO request);

    void delete(UUID uuid);
}