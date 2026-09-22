package com.spring.implementation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDoctor {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String specialization;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private BigDecimal consultationFee;

    public static CreateIamUserRequest toRequest(RegisterDoctor doctor) {
        return new CreateIamUserRequest(
                doctor.getEmail(),
                doctor.getEmail(),
                doctor.getFirstName(),
                doctor.getLastName(),
                "DOCTOR"
        );
    }
}