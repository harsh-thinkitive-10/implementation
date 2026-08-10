package com.spring.implementation.entity;


import com.spring.implementation.dto.PatientDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "patient")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String fullName;
    private Integer age;
    private String gender;
    private String phoneNumber;
    private String email;


    public static PatientDTO toDTO(Patient patient){
        return PatientDTO.builder()
                .fullName(patient.getFullName())
                .age(patient.getAge())
                .gender(patient.getGender())
                .phoneNumber(patient.getPhoneNumber())
                .email(patient.getEmail())
                .build();

    }

    public static Patient toEntity(PatientDTO patientDTO){
        return Patient.builder()
                .fullName(patientDTO.getFullName())
                .age(patientDTO.getAge())
                .gender(patientDTO.getGender())
                .phoneNumber(patientDTO.getPhoneNumber())
                .email(patientDTO.getEmail())
                .build();
    }

}
