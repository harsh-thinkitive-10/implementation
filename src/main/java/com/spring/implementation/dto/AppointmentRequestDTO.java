package com.spring.implementation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {

    @NotNull
    @Future
    private Instant appointmentDate;

    @NotBlank
    private String reasonForVisit;

    @NotNull
    private UUID patientUuid;

    @NotNull
    private UUID doctorUuid;

    @NotNull
    private UUID locationUuid;
}