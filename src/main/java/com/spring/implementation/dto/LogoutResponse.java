package com.spring.implementation.dto;


public record LogoutResponse(
        boolean success,
        String message
) {
}