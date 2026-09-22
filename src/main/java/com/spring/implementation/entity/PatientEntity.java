package com.spring.implementation.entity;

import com.spring.implementation.dto.PatientDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "patient")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private Integer age;

    private String gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Column(name = "keycloak_user_id", nullable = false)
    private String keycloakUserId;

    @JdbcTypeCode(Types.CHAR)
    @Column(
            name = "uuid",
            nullable = false,
            length = 36
    )
    private UUID uuid;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        if (isActive == null) {
            isActive = true;
        }
    }

    public static PatientDTO toDTO(PatientEntity patient) {

        return PatientDTO.builder()
                .uuid(patient.getUuid())
                .fullName(patient.getFullName())
                .age(patient.getAge())
                .gender(patient.getGender())
                .phoneNumber(patient.getPhoneNumber())
                .email(patient.getEmail())
                .isActive(Objects.nonNull(patient.getIsActive()) ? patient.isActive : true)
                .build();
    }

    public static PatientEntity toEntity(PatientDTO patientDTO) {
        return PatientEntity.builder()
                .fullName(patientDTO.getFullName())
                .age(patientDTO.getAge())
                .gender(patientDTO.getGender())
                .phoneNumber(patientDTO.getPhoneNumber())
                .email(patientDTO.getEmail())
                .build();
    }
}