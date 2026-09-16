package com.spring.implementation.dto;

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
public class DoctorProfileResponseDTO {

    private UUID uuid;
    private String fullName;
    private String specialization;
    private String phoneNumber;
    private String email;
    private BigDecimal consultationFee;
}
