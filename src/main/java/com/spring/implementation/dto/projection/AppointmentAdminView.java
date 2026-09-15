package com.spring.implementation.dto.projection;

import com.spring.implementation.dto.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public interface AppointmentAdminView {

    Instant getAppointmentDate();

    String getReasonForVisit();

    AppointmentStatus getStatus();

    // Patient
    String getPatientFullName();

    Integer getPatientAge();

    String getPatientGender();

    String getPatientPhoneNumber();

    String getPatientEmail();

    // Doctor
    String getDoctorFullName();

    String getDoctorSpecialization();

    String getDoctorPhoneNumber();

    String getDoctorEmail();

    BigDecimal getConsultationFee();
}