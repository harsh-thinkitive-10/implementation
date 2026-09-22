package com.spring.implementation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AdminDashboardDTO {

    private long totalPatients;
    private long totalDoctors;
    private long totalLocations;
    private long totalAppointments;
    private long todayAppointments;
    private long scheduledAppointments;
    private long completedAppointments;
    private long cancelledAppointments;
}