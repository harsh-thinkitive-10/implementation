package com.spring.implementation.service;

public interface PasswordResetService {
    void requestPasswordReset(String username);

    void resetPassword(
            String token,
            String newPassword
    );


    void sendSetPasswordEmail(
            String keycloakUserId,
            String email
    );

    void setPassword(String token, String newPassword);

}
