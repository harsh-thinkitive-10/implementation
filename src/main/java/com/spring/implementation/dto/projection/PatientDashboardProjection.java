package com.spring.implementation.dto.projection;

public interface PatientDashboardProjection {
    String getFullName();

    Long getUpcomingAppointments();

    Long getCompletedAppointments();

    Long getActivePrescriptions();

    Long getTotalMedicalRecords();

    Long getAvailableLabReports();

    String getNextAppointmentDate();

    String getDoctorName();

    String getSpecialization();
}
