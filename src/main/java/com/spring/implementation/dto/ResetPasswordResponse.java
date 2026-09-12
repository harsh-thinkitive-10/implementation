package com.spring.implementation.dto;

public record ResetPasswordResponse(
        boolean success,
        String message
) {
}
