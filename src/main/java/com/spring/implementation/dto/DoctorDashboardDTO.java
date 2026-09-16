package com.spring.implementation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDashboardDTO {

    private Long totalAppointments;
    private Long scheduledAppointments;
    private Long completedAppointments;
    private Long cancelledAppointments;
    private Long totalPatients;
}