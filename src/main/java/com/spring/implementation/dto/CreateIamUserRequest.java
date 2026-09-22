package com.spring.implementation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateIamUserRequest(

        @NotBlank
        String username,

        @NotBlank
        @Email
        String email,

        String firstName,

        String lastName,

        @NotBlank
        String role
) {
}