package com.spring.implementation.service;

import com.spring.implementation.dto.KeycloakUser;
import com.spring.implementation.dto.LoginDTO;
import com.spring.implementation.dto.LoginResponseDTO;
import com.spring.implementation.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface IamService {

    String createUser(RegisterRequest request) throws UserPrincipalNotFoundException;

    LoginResponseDTO login(LoginDTO request);

    void setPassword(String keycloakUserId, String newPassword);

    void changePassword(String keycloakUserId, String currentPassword, String newPassword);
    void resetPassword(
            String keycloakUserId,
            String newPassword
    );
    KeycloakUser findUserByUsername(String username);


}
