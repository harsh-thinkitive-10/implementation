package com.spring.implementation.controller;

import com.spring.implementation.dto.*;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.service.AuthService;
import com.spring.implementation.service.PasswordResetService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends AppController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody LoginDTO request, HttpServletResponse response) {

        TokenResponse token = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("__Host-refresh_token", token.refreshToken()).httpOnly(true).secure(true).sameSite("Lax").path("/").build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return data(ResponseCode.OK, "Login Successfull", LoginResponseDTO.builder().accessToken(token.accessToken()).expiresIn(token.expiresIn()).tokenType("Bearer").build());
    }

    @PostMapping("/set-password")
    public ResponseEntity<Void> setPassword(@Valid @RequestBody SetPasswordRequest request) {

        authService.setPassword(request.token(), request.newPassword());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/change-password")
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(@Valid @RequestBody ChangePasswordRequest request, @AuthenticationPrincipal Jwt jwt) {

        String keycloakUserId = jwt.getSubject();

        ChangePasswordResponseDTO response = authService.changePassword(keycloakUserId, request.currentPassword(), request.newPassword());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {

        passwordResetService.requestPasswordReset(request.username());

        return ResponseEntity.ok(new ForgotPasswordResponse(true, "If an account exists, a password reset link has been sent."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {

        passwordResetService.resetPassword(request.token(), request.newPassword());

        return ResponseEntity.ok(new ResetPasswordResponse(true, "Password reset successfully."));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Response> refresh(@CookieValue("__Host-refresh_token") String refreshToken, HttpServletResponse response) {

        TokenResponse tokens = authService.refreshToken(refreshToken);

        ResponseCookie cookie = ResponseCookie.from("__Host-refresh_token", tokens.refreshToken()).httpOnly(true).secure(true).sameSite("Lax").path("/").maxAge(Duration.ofDays(7)).build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return data(ResponseCode.OK, "refresh Successfull", LoginResponseDTO.builder().accessToken(tokens.accessToken()).expiresIn(tokens.expiresIn()).tokenType("Bearer").build());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("__Host-refresh_token") String refreshToken, HttpServletResponse response) {

        authService.logout(refreshToken);

        ResponseCookie cookie = ResponseCookie.from("__Host-refresh_token", "").httpOnly(true).secure(true).sameSite("Lax").path("/").maxAge(0).build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.noContent().build();
    }

}
