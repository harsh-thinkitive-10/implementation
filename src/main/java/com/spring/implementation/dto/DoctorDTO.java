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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {

    @NotBlank
    private String fullName;

    @NotBlank
    private String specialization;

    @NotBlank
    private String phoneNumber;

    @Email(message = "invalid email")
    private String email;

    private BigDecimal consultationFee;
}