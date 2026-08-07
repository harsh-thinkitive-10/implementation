package com.spring.implementation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {

    @Future
    private LocalDate appointmentDate;
    @Future
    private LocalTime appointmentTime;
    private String reasonForVisit;
    private String status;

    @NotBlank
    private Long patientId;

    @NotNull
    private Long doctorId;

}