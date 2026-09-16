package com.spring.implementation.service;

import com.spring.implementation.dto.*;

public interface AuthService {

    String registerPatient(RegisterPatient patient);

    LoginResponseDTO login(LoginDTO request);

    void setPassword(String token, String newPassword);

    ChangePasswordResponseDTO changePassword(String userId, String currentPassword, String newPassword);

    void logout(String refreshToken);

    TokenResponse refreshToken(String refreshToken);


}
