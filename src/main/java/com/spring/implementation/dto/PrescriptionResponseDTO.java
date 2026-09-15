package com.spring.implementation.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponseDTO {

    private UUID uuid;
    private String medicineName;
    private String dosage;
    private String frequency;
    private Integer duration;
    private String instructions;
    private UUID appointmentUuid;
}