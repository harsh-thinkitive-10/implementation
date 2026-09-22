package com.spring.implementation.dto;

import com.spring.implementation.dto.enums.TaxIdType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxDTO {

    private UUID uuid;

    @NotBlank(message = "Tax ID is required")
    private String taxId;

    @NotBlank(message = "Tax name is required")
    private String name;

    @NotNull(message = "Tax ID type is required")
    private TaxIdType type;

    private String npi;

    @Valid
    private AddressDTO billingAddress;
}