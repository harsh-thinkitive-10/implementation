package com.spring.implementation.service;

import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.dto.RegisterRequest;

public interface KeycloakAdminService {

    void testConnection();

    String createUser(RegisterRequest request);

}
