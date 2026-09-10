package com.spring.implementation.service.impl;

import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.dto.PatientDashboardDTO;
import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.dto.projection.PatientDashboardProjection;
import com.spring.implementation.entity.PatientEntity;
import com.spring.implementation.exception.PatientNotFoundException;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.AuthService;
import com.spring.implementation.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Builder
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;
    private final AuthService authService;


    @Override
    @Cacheable(cacheNames = "get patient")
    public List<PatientDTO> getAllPatient() {
        List<PatientEntity> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for(PatientEntity patient : patients){
            patientDTOS.add(PatientEntity.toDTO(patient));
        }
        return patientDTOS;
    }

    @Override
    @Transactional()
    public PatientDTO getPatientById(Long id) {
        PatientEntity patient = patientRepository.findById(id).orElseThrow(()->new PatientNotFoundException(String.format("Patient not found with id %d",id)));
        return PatientEntity.toDTO(patient);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public PatientDTO registerNewPatient(@NonNull RegisterPatient patientRequest) {
        String userId = authService.registerPatient(patientRequest);
        PatientEntity patient = PatientEntity.builder().fullName(patientRequest.getFirstName()+" " + patientRequest.getLastName())
                .age(patientRequest.getAge())
                .gender(patientRequest.getGender())
                .phoneNumber(patientRequest.getPhoneNumber())
                .email(patientRequest.getEmail())
                .keycloakUserId(userId)
                .build();
        return PatientEntity.toDTO(patientRepository.save(patient));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public PatientDTO updatePatient(String keycloakUserId, PatientDTO patientDTO) {
        PatientEntity patient = patientRepository.findByKeycloakUserId(keycloakUserId).orElseThrow(() -> new RuntimeException("Patient not found."));

        if (patientDTO.getFullName() != null) {
            patient.setFullName(patientDTO.getFullName());
        }

        if (patientDTO.getGender() != null) {
            patient.setGender(patientDTO.getGender());
        }

        if (patientDTO.getEmail() != null) {
            patient.setEmail(patientDTO.getEmail());
        }

        if (patientDTO.getAge() != null) {
            patient.setAge(patientDTO.getAge());
        }

        if (patientDTO.getPhoneNumber() != null) {
            patient.setPhoneNumber(patientDTO.getPhoneNumber());
        }

        return PatientEntity.toDTO(patientRepository.save(patient));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void deletePatient(Long id) {
        PatientEntity patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found."));
        patientRepository.delete(patient);
    }

    @Override
    public PatientDTO GetKeyCloakId(String keyCloakUserId) {
        PatientEntity patient = patientRepository.findByKeycloakUserId(keyCloakUserId).orElseThrow(()->new PatientNotFoundException("Patient Not Found"));
        return PatientEntity.toDTO(patient);
    }
    @Override
    public String getKeycloakUserId(Long patientId) {

        String keycloakUserId =
                patientRepository.findKeycloakUserIdByPatientId(patientId);

        if (keycloakUserId == null) {
            throw new RuntimeException(
                    "Keycloak user not found for patient ID: "
                            + patientId
            );
        }

        return keycloakUserId;
    }

    @Override
    public PatientDashboardDTO getMyDashboard(
            String keycloakUserId
    ) {

        PatientDashboardProjection projection =
                patientRepository.getPatientDashboard(
                        keycloakUserId
                );

        return PatientDashboardDTO.toDTO(projection);
    }
}
