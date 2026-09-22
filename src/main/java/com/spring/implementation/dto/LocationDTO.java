package com.spring.implementation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private UUID uuid;

    @NotBlank(message = "Location code is required")
    private String code;

    @NotBlank(message = "Location name is required")
    private String name;

    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    private String npi;

    @Valid
    private AddressDTO billingAddress;

    private TaxDTO taxEntity;
}