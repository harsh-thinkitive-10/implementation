package com.spring.implementation.entity;

import com.spring.implementation.dto.DoctorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "doctor")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    private String fullName;
    private String specialization;
    private String phoneNumber;
    private String email;
    private BigDecimal consultationFee;

    public static DoctorDTO toDto(Doctor doctor){
        return DoctorDTO.builder()
                .fullName(doctor.getFullName())
                .specialization(doctor.getSpecialization())
                .phoneNumber(doctor.getPhoneNumber())
                .email(doctor.getEmail())
                .consultationFee(doctor.getConsultationFee())
                .build();
    }

    public static Doctor toEntity(DoctorDTO doctorDTO){
        return Doctor.builder()
                .fullName(doctorDTO.getFullName())
                .specialization(doctorDTO.getSpecialization())
                .phoneNumber(doctorDTO.getPhoneNumber())
                .email(doctorDTO.getEmail())
                .consultationFee(doctorDTO.getConsultationFee())
                .build();
    }
}
