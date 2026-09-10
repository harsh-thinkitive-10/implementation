package com.spring.implementation.controller;

import com.spring.implementation.dto.*;
import com.spring.implementation.service.AuthService;
import com.spring.implementation.service.IamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final IamService iamService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/set-password/{patientId}")
    public ResponseEntity<Void> setPassword(
            @PathVariable Long patientId,
            @Valid @RequestBody SetPasswordDTO request) {

        authService.setPassword(
                patientId,
                request.getNewPassword()
        );

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {

        String keycloakUserId = jwt.getSubject();

        ChangePasswordResponseDTO response =
                authService.changePassword(
                        keycloakUserId,
                        request.currentPassword(),
                        request.newPassword()
                );

        return ResponseEntity.ok(response);
    }

}
