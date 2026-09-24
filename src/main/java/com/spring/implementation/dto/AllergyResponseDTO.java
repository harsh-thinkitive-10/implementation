package com.spring.implementation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class AllergyResponseDTO {

    private UUID uuid;
    private UUID patientUuid;
    private String name;
    private String allergen;
    private String reaction;
    private String severity;
    private Instant onsetDate;
    private String source;
    private String status;
    private String notes;
}