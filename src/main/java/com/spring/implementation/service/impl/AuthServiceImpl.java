package com.spring.implementation.service.impl;

import com.spring.implementation.dto.DoctorDTO;
import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.service.AuthService;
import com.spring.implementation.service.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KeycloakAdminService keycloakAdminService;

    @Override
    public String registerPatient(RegisterPatient registerPatient) {
        return keycloakAdminService.createUser(RegisterPatient.toRequest(registerPatient));
    }

    @Override
    public DoctorDTO registerDoctor() {
        return null;
    }
}
