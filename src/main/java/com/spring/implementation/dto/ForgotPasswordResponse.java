package com.spring.implementation.dto;

public record ForgotPasswordResponse(
        boolean success,
        String message
) {
}
