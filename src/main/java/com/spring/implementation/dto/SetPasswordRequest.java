package com.spring.implementation.dto;

import jakarta.validation.constraints.NotBlank;

public record SetPasswordRequest(

        @NotBlank
        String token,

        @NotBlank
        String newPassword

) {
}