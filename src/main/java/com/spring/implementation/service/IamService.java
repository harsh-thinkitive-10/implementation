package com.spring.implementation.service;

import com.spring.implementation.dto.*;
import com.spring.implementation.dto.CreateIamUserRequest;

public interface IamService {

    String createUser(CreateIamUserRequest request);

    TokenResponse login(LoginDTO request);

    void setPassword(
            String keycloakUserId,
            String newPassword
    );

    void changePassword(
            String keycloakUserId,
            String currentPassword,
            String newPassword
    );

    void resetPassword(
            String keycloakUserId,
            String newPassword
    );

    KeycloakUser findUserByUsername(String username);

    TokenResponse refreshToken(String refreshToken);

    void logout(String refreshToken);

    void updateUser(
            String keycloakUserId,
            String fullName,
            String email
    );
}