package com.spring.implementation.dto.projection;

import com.spring.implementation.dto.enums.AppointmentStatus;

import java.time.Instant;

public interface AppointmentDoctorView {

    Instant getAppointmentDate();

    String getReasonForVisit();

    AppointmentStatus getStatus();

    // Patient
    String getPatientFullName();

    Integer getPatientAge();

    String getPatientGender();

    String getPatientPhoneNumber();

    String getPatientEmail();
}
