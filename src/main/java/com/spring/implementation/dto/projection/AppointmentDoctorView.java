package com.spring.implementation.dto.projection;

import com.spring.implementation.dto.enums.AppointmentStatus;

import java.time.Instant;
import java.util.UUID;

public interface AppointmentDoctorView {

    UUID getUuid();
    UUID getPatientUuid();

    Instant getAppointmentDate();
    String getReasonForVisit();
    AppointmentStatus getStatus();

    String getPatientFullName();
    Integer getPatientAge();
    String getPatientGender();
    String getPatientPhoneNumber();
    String getPatientEmail();

    UUID getLocationUuid();
    String getLocationCode();
    String getLocationName();
}