package com.spring.implementation.dto;

import com.spring.implementation.dto.enums.AppointmentStatus;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AppointmentFilterDTO {

    private String search;
    private AppointmentStatus status;
    private UUID patientUuid;
    private UUID doctorUuid;
    private UUID locationUuid;
    private Instant fromDate;
    private Instant toDate;
}