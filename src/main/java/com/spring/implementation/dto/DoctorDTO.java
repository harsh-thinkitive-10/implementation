package com.spring.implementation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private UUID uuid;
    private String fullName;
    private String specialization;
    private String phoneNumber;
    private String email;
    private BigDecimal consultationFee;
    private Boolean isActive;
}