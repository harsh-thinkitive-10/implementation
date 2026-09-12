package com.spring.implementation.controller;


import com.spring.implementation.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class EmailTestController {

    private final EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<String> testEmail(
            @RequestParam String email
    ) {

        emailService.sendPasswordResetEmail(
                email,
                "test-token-123"
        );

        return ResponseEntity.ok(
                "Email sent successfully"
        );
    }
}