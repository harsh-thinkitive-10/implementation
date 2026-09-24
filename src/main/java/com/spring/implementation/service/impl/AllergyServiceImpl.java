package com.spring.implementation.service.impl;

import com.spring.implementation.dto.AllergyRequestDTO;
import com.spring.implementation.dto.AllergyResponseDTO;
import com.spring.implementation.entity.AllergyEntity;
import com.spring.implementation.entity.PatientEntity;
import com.spring.implementation.repository.AllergyRepository;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.AllergyService;
import com.spring.implementation.dto.projection.AllergyView;
import com.spring.implementation.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AllergyServiceImpl implements AllergyService {

    private final AllergyRepository allergyRepository;
    private final PatientRepository patientRepository;


    @Override
    public AllergyResponseDTO create(AllergyRequestDTO request) {
        PatientEntity patient = patientRepository.findByUuidAndIsActiveTrue(request.getPatientUuid()).orElseThrow(() -> new RuntimeException("Patient not found"));

        return allergyRepository.save(AllergyEntity.toEntity(request, patient)).toDTO();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllergyResponseDTO> getByPatient(UUID patientUuid, Pageable pageable) {
        PatientEntity patient = patientRepository.findByUuid(patientUuid).orElseThrow(() -> new RuntimeException("Patient not found"));

        return allergyRepository.findByPatientId(patient.getPatientId(), pageable).map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public AllergyResponseDTO getByUuid(UUID uuid) {
        return allergyRepository.findViewByUuid(uuid).map(this::toDTO).orElseThrow(() -> new RuntimeException("Allergy not found"));
    }

    @Override
    public AllergyResponseDTO update(UUID uuid, AllergyRequestDTO request) {
        AllergyEntity allergy = allergyRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("Allergy not found"));

        allergy.setName(request.getName());
        allergy.setAllergen(request.getAllergen());
        allergy.setReaction(request.getReaction());
        allergy.setSeverity(request.getSeverity());
        allergy.setOnsetDate(request.getOnsetDate());
        allergy.setSource(request.getSource());
        allergy.setStatus(request.getStatus());
        allergy.setNotes(request.getNotes());

        return allergy.toDTO();
    }

    @Override
    public void delete(UUID uuid) {
        if (allergyRepository.deleteByUuid(uuid) == 0) throw new RuntimeException("Allergy not found");
    }

    private AllergyResponseDTO toDTO(AllergyView view) {
        return new AllergyResponseDTO(view.getUuid(), null, view.getName(), view.getAllergen(), view.getReaction(), view.getSeverity(), view.getOnsetDate(), view.getSource(), view.getStatus(), view.getNotes());
    }
}