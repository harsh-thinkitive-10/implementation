package com.spring.implementation.service.impl;

import com.spring.implementation.config.PasswordResetTokenGenerator;
import com.spring.implementation.config.PasswordResetTokenHasher;
import com.spring.implementation.dto.KeycloakUser;
import com.spring.implementation.entity.PasswordResetTokenEntity;
import com.spring.implementation.exception.InvalidPasswordResetTokenException;
import com.spring.implementation.repository.PasswordResetTokenRepository;
import com.spring.implementation.service.EmailService;
import com.spring.implementation.service.IamService;
import com.spring.implementation.service.PasswordResetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
    private static final int TOKEN_EXPIRY_MINUTES = 15;

    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordResetTokenGenerator tokenGenerator;
    private final PasswordResetTokenHasher tokenHasher;
    private final EmailService emailService;

    // We will add this next
    private final IamService iamService;

    @Value("${app.frontend-url}")
    private String resetPasswordUrl;

    @Override
    @Transactional
    public void requestPasswordReset(String username) {

        KeycloakUser user =
                iamService.findUserByUsername(username);

        if (user == null) {
            return;
        }

        tokenRepository.deleteActiveTokens(user.userId());

        String rawToken =
                tokenGenerator.generate();

        String tokenHash =
                tokenHasher.hash(rawToken);

        LocalDateTime expiresAt =
                LocalDateTime.now()
                        .plusMinutes(TOKEN_EXPIRY_MINUTES);

        PasswordResetTokenEntity resetToken =
                PasswordResetTokenEntity.builder()
                        .keycloakUserId(user.userId())
                        .tokenHash(tokenHash)
                        .expiresAt(expiresAt)
                        .createdAt(LocalDateTime.now())
                        .build();

        String resetLink =
                UriComponentsBuilder
                        .fromUriString(resetPasswordUrl)
                        .queryParam("token", rawToken)
                        .build()
                        .toUriString();

        tokenRepository.save(resetToken);

        emailService.sendPasswordResetEmail(
                user.email(),
                resetLink
        );
    }

    @Transactional
    @Override
    public void resetPassword(String token, String newPassword) {

        LocalDateTime now = LocalDateTime.now();

        String tokenHash = tokenHasher.hash(token);

        PasswordResetTokenEntity resetToken =
                tokenRepository.findByTokenHash(tokenHash)
                        .orElseThrow(() ->
                                new InvalidPasswordResetTokenException(
                                        "Invalid or expired password reset token"
                                ));

        if (resetToken.getUsedAt() != null) {
            throw new InvalidPasswordResetTokenException(
                    "Invalid or expired password reset token"
            );
        }

        if (!resetToken.getExpiresAt().isAfter(now)) {
            throw new InvalidPasswordResetTokenException(
                    "Invalid or expired password reset token"
            );
        }

        // Keycloak owns the actual password.
        iamService.resetPassword(
                resetToken.getKeycloakUserId(),
                newPassword
        );

        // Consume the token only after Keycloak succeeds.
        resetToken.setUsedAt(now);
    }
}
