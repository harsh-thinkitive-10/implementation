package com.spring.implementation.entity;

import com.spring.implementation.dto.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "appointment")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    private Instant appointmentDate;
    private String reasonForVisit;

    @Enumerated(value = EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;
}
