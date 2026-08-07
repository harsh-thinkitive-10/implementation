package com.spring.implementation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Long doctorId;

    @NotBlank
    private String fullName;

    @NotBlank
    private String specialization;

    @NotBlank
    private String phoneNumber;

    @Email(message = "invalid email")
    private String email;

    private Double consultationFee;
}