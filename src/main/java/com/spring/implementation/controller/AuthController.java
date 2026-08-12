package com.spring.implementation.controller;

import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.service.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KeycloakAdminService keycloakAdminService;

    @PostMapping("/patient/register")
    public ResponseEntity<String> register(@RequestBody RegisterPatient patient) {
        return null;
    }

}
