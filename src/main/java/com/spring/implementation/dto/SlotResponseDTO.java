package com.spring.implementation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class SlotResponseDTO {
    private Instant startTime;
    private Instant endTime;
}