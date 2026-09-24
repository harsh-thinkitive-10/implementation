package com.spring.implementation.service.impl;

import com.spring.implementation.config.PasswordResetTokenGenerator;
import com.spring.implementation.config.PasswordResetTokenHasher;
import com.spring.implementation.dto.KeycloakUser;
import com.spring.implementation.dto.enums.PasswordTokenType;
import com.spring.implementation.entity.PasswordResetTokenEntity;
import com.spring.implementation.exception.InvalidPasswordResetTokenException;
import com.spring.implementation.repository.PasswordResetTokenRepository;
import com.spring.implementation.service.EmailService;
import com.spring.implementation.service.IamService;
import com.spring.implementation.service.PasswordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private static final int TOKEN_EXPIRY_MINUTES = 15;

    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordResetTokenGenerator tokenGenerator;
    private final PasswordResetTokenHasher tokenHasher;
    private final EmailService emailService;
    private final IamService iamService;

    @Value("${app.frontend-url}")
    private String resetPasswordUrl;

    @Override
    @Transactional
    public void requestPasswordReset(String username) {

        KeycloakUser user = iamService.findUserByUsername(username);

        if (user == null) {
            return;
        }

        tokenRepository.deleteActiveTokens(user.userId());

        String rawToken = tokenGenerator.generate();

        String tokenHash = tokenHasher.hash(rawToken);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime expiresAt = now.plusMinutes(TOKEN_EXPIRY_MINUTES);

        PasswordResetTokenEntity resetToken = PasswordResetTokenEntity.builder().keycloakUserId(user.userId()).tokenHash(tokenHash).tokenType(PasswordTokenType.PASSWORD_RESET).expiresAt(expiresAt).createdAt(now).build();

        String resetLink = UriComponentsBuilder.fromUriString(resetPasswordUrl).queryParam("token", rawToken).build().toUriString();

        tokenRepository.save(resetToken);

        emailService.sendPasswordResetEmail(user.email(), resetLink);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {

        LocalDateTime now = LocalDateTime.now();

        String tokenHash = tokenHasher.hash(token);

        PasswordResetTokenEntity resetToken = tokenRepository.findByTokenHashForUpdate(tokenHash).orElseThrow(() -> new InvalidPasswordResetTokenException("Invalid or expired password reset token"));

        if (resetToken.getTokenType() != PasswordTokenType.PASSWORD_RESET) {

            throw new InvalidPasswordResetTokenException("Invalid or expired password reset token");
        }

        if (resetToken.getUsedAt() != null) {

            throw new InvalidPasswordResetTokenException("Invalid or expired password reset token");
        }

        if (!resetToken.getExpiresAt().isAfter(now)) {

            throw new InvalidPasswordResetTokenException("Invalid or expired password reset token");
        }

        iamService.resetPassword(resetToken.getKeycloakUserId(), newPassword);

        resetToken.setUsedAt(now);
    }

    @Override
    @Transactional
    public void sendSetPasswordEmail(String keycloakUserId, String email) {

        tokenRepository.deleteActiveTokens(keycloakUserId);

        String rawToken = tokenGenerator.generate();

        String tokenHash = tokenHasher.hash(rawToken);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime expiresAt = now.plusMinutes(TOKEN_EXPIRY_MINUTES);

        PasswordResetTokenEntity setPasswordToken = PasswordResetTokenEntity.builder().keycloakUserId(keycloakUserId).tokenHash(tokenHash).tokenType(PasswordTokenType.SET_PASSWORD).expiresAt(expiresAt).createdAt(now).build();

        String setPasswordLink = UriComponentsBuilder.fromUriString(resetPasswordUrl).queryParam("token", rawToken).build().toUriString();

        tokenRepository.save(setPasswordToken);

        emailService.sendPasswordResetEmail(email, setPasswordLink);
    }

    @Override
    @Transactional
    public void setPassword(String token, String newPassword) {

        LocalDateTime now = LocalDateTime.now();

        String tokenHash = tokenHasher.hash(token);

        PasswordResetTokenEntity setPasswordToken = tokenRepository.findByTokenHashForUpdate(tokenHash).orElseThrow(() -> new InvalidPasswordResetTokenException("Invalid or expired password setup token"));

        if (setPasswordToken.getTokenType() != PasswordTokenType.SET_PASSWORD) {

            throw new InvalidPasswordResetTokenException("Invalid or expired password setup token");
        }

        if (setPasswordToken.getUsedAt() != null) {

            throw new InvalidPasswordResetTokenException("Invalid or expired password setup token");
        }

        if (!setPasswordToken.getExpiresAt().isAfter(now)) {

            throw new InvalidPasswordResetTokenException("Invalid or expired password setup token");
        }

        iamService.setPassword(setPasswordToken.getKeycloakUserId(), newPassword);

        setPasswordToken.setUsedAt(now);
    }
}