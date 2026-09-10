package com.spring.implementation.dto;
import com.spring.implementation.dto.projection.AppointmentView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reasonForVisit;
    private String status;
    private PatientDTO patient;
    private DoctorDTO doctor;

}