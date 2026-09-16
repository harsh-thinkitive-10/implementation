package com.spring.implementation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionRequestDTO {

    @NotBlank
    private String medicineName;

    @NotBlank
    private String dosage;

    @NotBlank
    private String frequency;

    @NotNull
    @Positive
    private Integer duration;

    private String instructions;

    @NotNull
    private UUID appointmentUuid;

}