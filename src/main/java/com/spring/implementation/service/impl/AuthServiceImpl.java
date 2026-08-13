package com.spring.implementation.service.impl;

import com.spring.implementation.dto.LoginDTO;
import com.spring.implementation.dto.LoginResponseDTO;
import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.AuthService;
import com.spring.implementation.service.IamService;
import com.spring.implementation.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final IamService iamService;
    private final PatientRepository patientRepository;


    @Override
    public String registerPatient(RegisterPatient registerPatient) {
        try {
            return iamService.createUser(RegisterPatient.toRequest(registerPatient));
        } catch (UserPrincipalNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoginResponseDTO login(LoginDTO request) {
        return iamService.login(request);
    }

    @Override
    public void setPassword(Long patientId, String newPassword) {

        String keycloakUserId =
                patientRepository.findKeycloakUserIdByPatientId(patientId);

        if (keycloakUserId == null) {
            throw new RuntimeException(
                    "Keycloak user not found for patient ID: " + patientId
            );
        }

        iamService.setPassword(
                keycloakUserId,
                newPassword
        );
    }
}
