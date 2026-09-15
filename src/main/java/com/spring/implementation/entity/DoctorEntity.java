package com.spring.implementation.entity;

import com.spring.implementation.dto.DoctorDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "doctor")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    @JdbcTypeCode(Types.CHAR)
    @Column(
            name = "uuid",
            nullable = false,
            length = 36
    )
    private UUID uuid;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String specialization;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Column(name = "consultation_fee")
    private BigDecimal consultationFee;

    @Column(
            name = "keycloak_user_id",
            nullable = false
    )
    private String keycloakUserId;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

    public static DoctorDTO toDto(DoctorEntity doctor) {
        return DoctorDTO.builder()
                .fullName(doctor.getFullName())
                .specialization(doctor.getSpecialization())
                .phoneNumber(doctor.getPhoneNumber())
                .email(doctor.getEmail())
                .consultationFee(doctor.getConsultationFee())
                .build();
    }

    public static DoctorEntity toEntity(DoctorDTO doctorDTO) {
        return DoctorEntity.builder()
                .fullName(doctorDTO.getFullName())
                .specialization(doctorDTO.getSpecialization())
                .phoneNumber(doctorDTO.getPhoneNumber())
                .email(doctorDTO.getEmail())
                .consultationFee(doctorDTO.getConsultationFee())
                .build();
    }
}