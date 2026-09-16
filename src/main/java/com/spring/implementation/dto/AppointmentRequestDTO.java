package com.spring.implementation.dto;

import com.spring.implementation.dto.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private AppointmentStatus status;

    @NotNull
    private UUID patientUuid;

    @NotNull
    private UUID doctorUuid;
}