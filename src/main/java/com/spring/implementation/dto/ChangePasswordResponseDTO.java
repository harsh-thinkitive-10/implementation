package com.spring.implementation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChangePasswordResponseDTO {

    private boolean success;

    private String message;
}