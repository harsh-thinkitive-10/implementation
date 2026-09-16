package com.spring.implementation.dto.projection;

public interface DoctorDashboardView {

    Long getTotalAppointments();

    Long getScheduledAppointments();

    Long getCompletedAppointments();

    Long getCancelledAppointments();

    Long getTotalPatients();
}