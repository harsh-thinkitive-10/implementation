package com.spring.implementation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AllergyRequestDTO {

    @NotNull
    private UUID patientUuid;

    @NotBlank
    private String name;

    private String allergen;
    private String reaction;
    private String severity;
    private Instant onsetDate;
    private String source;
    private String status;
    private String notes;
}