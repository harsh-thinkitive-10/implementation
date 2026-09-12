package com.spring.implementation.service;

public interface PasswordResetService {
    void requestPasswordReset(String username);

    void resetPassword(
            String token,
            String newPassword
    );
}
