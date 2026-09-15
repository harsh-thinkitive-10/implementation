package com.spring.implementation.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {

    private UUID uuid;

    private Instant appointmentDate;

    private String reasonForVisit;

    private String status;

    private PatientDTO patient;

    private DoctorDTO doctor;
}