package com.spring.implementation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SetPasswordDTO {
    @NotBlank
    private String newPassword;
}
