package com.spring.implementation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private UUID uuid;

    private String fullName;
    private Integer age;
    private String gender;
    private String phoneNumber;
    private String email;
    private Boolean isActive;
}