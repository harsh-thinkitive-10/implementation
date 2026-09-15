package com.spring.implementation.dto;

import com.spring.implementation.dto.enums.AppointmentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {

    @Future
    private Instant appointmentDate;

    @NotBlank
    private String reasonForVisit;
    private AppointmentStatus status;

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

}