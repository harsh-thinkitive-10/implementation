package com.spring.implementation.service.impl;

import com.spring.implementation.dto.*;
import com.spring.implementation.exception.NewAndOldPasswordSameException;
import com.spring.implementation.service.AuthService;
import com.spring.implementation.service.IamService;
import com.spring.implementation.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final IamService iamService;
    private final PasswordService passwordService;


    @Override
    public TokenResponse login(LoginDTO request) {
        return iamService.login(request);
    }

    @Override
    public void setPassword(String token, String newPassword) {
        passwordService.setPassword(token, newPassword);
    }

    @Override
    public ChangePasswordResponseDTO changePassword(String keycloakUserId, String currentPassword, String newPassword) {
        if (currentPassword.equals(newPassword)) {
            throw new NewAndOldPasswordSameException("New password must be different from current password");
        }

        iamService.changePassword(keycloakUserId, currentPassword, newPassword);
        return ChangePasswordResponseDTO.builder().success(true).message("Password changed successfully").build();
    }

    @Override
    public void logout(String refreshToken) {
        iamService.logout(refreshToken);
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        return iamService.refreshToken(refreshToken);
    }

}
