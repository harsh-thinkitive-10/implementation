package com.spring.implementation.service.impl;

import com.spring.implementation.dto.CreateIamUserRequest;
import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.dto.PatientDashboardDTO;
import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.dto.projection.PatientDashboardProjection;
import com.spring.implementation.entity.PatientEntity;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.exception.PatientNotFoundException;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.AuthService;
import com.spring.implementation.service.IamService;
import com.spring.implementation.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Builder
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;
    private final AuthService authService;
    private final IamService iamService;


    @Override
    @Transactional
    public PatientDTO registerNewPatient(RegisterPatient patientRequest) {

        CreateIamUserRequest iamRequest =
                RegisterPatient.toRequest(patientRequest);

        String keycloakUserId =
                iamService.createUser(iamRequest);

        PatientEntity patient = PatientEntity.builder()
                .fullName(patientRequest.getFirstName()+" "+patientRequest.getLastName())
                .age(patientRequest.getAge())
                .gender(patientRequest.getGender())
                .phoneNumber(patientRequest.getPhoneNumber())
                .email(patientRequest.getEmail())
                .keycloakUserId(keycloakUserId)
                .build();

        PatientEntity savedPatient =
                patientRepository.save(patient);

        return PatientEntity.toDTO(savedPatient);
    }

    @Override
    public Page<PatientDTO> getAllPatient(
            String search,
            String gender,
            Integer age,
            Pageable pageable
    ) {
        Page<PatientEntity> patients =
                patientRepository.findPatients(
                        search,
                        gender,
                        age,
                        pageable
                );

        return patients.map(PatientEntity::toDTO);
    }

    @Override
    @Transactional()
    public PatientDTO getPatientById(Long id) {
        PatientEntity patient = patientRepository.findById(id).orElseThrow(()->new PatientNotFoundException(String.format("Patient not found with id %d",id)));
        return PatientEntity.toDTO(patient);
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

    @Override
    @Transactional
    public PatientDTO updatePatient(UUID uuid, PatientDTO patientDTO) throws ImplException {
        PatientEntity patient = patientRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new ImplException(ResponseCode.NOT_FOUND,"Patient not found"));

        patient.setFullName(patientDTO.getFullName());
        patient.setAge(patientDTO.getAge());
        patient.setGender(patientDTO.getGender());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setEmail(patientDTO.getEmail());

        iamService.updateUser(
                patient.getKeycloakUserId(),
                patient.getFullName(),
                patient.getEmail()
        );

        PatientEntity updatedPatient = patientRepository.save(patient);

        return PatientEntity.toDTO(updatedPatient);
    }

    @Override
    @Transactional
    public void deletePatient(UUID uuid) throws ImplException {
        PatientEntity patient = patientRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new ImplException(ResponseCode.NOT_FOUND,"Patient not found"));

        patient.setIsActive(false);
        patientRepository.save(patient);
    }
}
