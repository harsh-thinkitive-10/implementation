package com.spring.implementation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {

    @NotBlank(message = "Name is required")
    private String fullName;

    @Min(value = 00)
    @Max(value = 60)
    private Integer age;

    private String gender;

    private String phoneNumber;

    @Email(message = "invalid mail")
    private String email;
}
