package com.spring.implementation.entity;


import com.spring.implementation.dto.PatientDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "patient")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String fullName;
    private Integer age;
    private String gender;
    private String phoneNumber;
    private String email;
    @Column(name = "keycloak_user_id", unique = true, nullable = false)
    private String keycloakUserId;


    public static PatientDTO toDTO(PatientEntity patient){
        return PatientDTO.builder()
                .fullName(patient.getFullName())
                .age(patient.getAge())
                .gender(patient.getGender())
                .phoneNumber(patient.getPhoneNumber())
                .email(patient.getEmail())
                .build();

    }

    public static PatientEntity toEntity(PatientDTO patientDTO){
        return PatientEntity.builder()
                .fullName(patientDTO.getFullName())
                .age(patientDTO.getAge())
                .gender(patientDTO.getGender())
                .phoneNumber(patientDTO.getPhoneNumber())
                .email(patientDTO.getEmail())
                .build();
    }

}
