package com.spring.implementation.service;

import com.spring.implementation.dto.*;

public interface AuthService {

    String registerPatient(RegisterPatient patient);
    LoginResponseDTO login(LoginDTO request);
    void setPassword(Long patientId, String newPassword);
    ChangePasswordResponseDTO changePassword(String userId, String currentPassword, String newPassword);


}
