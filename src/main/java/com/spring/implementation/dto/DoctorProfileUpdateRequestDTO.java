package com.spring.implementation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorProfileUpdateRequestDTO {

    @NotBlank
    private String fullName;

    @NotBlank
    private String specialization;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain exactly 10 digits")
    private String phoneNumber;

    @NotBlank
    private String email;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal consultationFee;
}
