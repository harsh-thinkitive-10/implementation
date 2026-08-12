package com.spring.implementation.entity;

import com.spring.implementation.dto.DoctorDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "doctor")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    private String fullName;
    private String specialization;
    private String phoneNumber;
    private String email;
    private BigDecimal consultationFee;

    @Column(
            name = "keycloak_user_id",
            nullable = false,
            unique = true
    )
    private String keycloakUserId;

    public static DoctorDTO toDto(DoctorEntity doctor){
        return DoctorDTO.builder()
                .fullName(doctor.getFullName())
                .specialization(doctor.getSpecialization())
                .phoneNumber(doctor.getPhoneNumber())
                .email(doctor.getEmail())
                .consultationFee(doctor.getConsultationFee())
                .build();
    }

    public static DoctorEntity toEntity(DoctorDTO doctorDTO){
        return DoctorEntity.builder()
                .fullName(doctorDTO.getFullName())
                .specialization(doctorDTO.getSpecialization())
                .phoneNumber(doctorDTO.getPhoneNumber())
                .email(doctorDTO.getEmail())
                .consultationFee(doctorDTO.getConsultationFee())
                .build();
    }
}
