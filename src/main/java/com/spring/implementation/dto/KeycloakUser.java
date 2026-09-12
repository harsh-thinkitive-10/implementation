package com.spring.implementation.dto;

public record KeycloakUser(
        String userId,
        String username,
        String email
) {
}
