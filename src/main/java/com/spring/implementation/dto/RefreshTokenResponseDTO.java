package com.spring.implementation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshTokenResponseDTO(
        boolean success,
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("expires_in")
        long expiresIn,
        @JsonProperty("token_type")
        String tokenType
) {
}
