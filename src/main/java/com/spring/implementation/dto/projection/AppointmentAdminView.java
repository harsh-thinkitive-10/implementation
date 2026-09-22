package com.spring.implementation.dto.projection;

import com.spring.implementation.dto.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface AppointmentAdminView {

    UUID getUuid();
    UUID getPatientUuid();
    UUID getDoctorUuid();

    Instant getAppointmentDate();
    String getReasonForVisit();
    AppointmentStatus getStatus();

    String getPatientFullName();
    Integer getPatientAge();
    String getPatientGender();
    String getPatientPhoneNumber();
    String getPatientEmail();

    String getDoctorFullName();
    String getDoctorSpecialization();
    String getDoctorPhoneNumber();
    String getDoctorEmail();
    BigDecimal getConsultationFee();

    UUID getLocationUuid();
    String getLocationCode();
    String getLocationName();
}