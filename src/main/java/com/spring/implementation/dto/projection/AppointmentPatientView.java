package com.spring.implementation.dto.projection;

import com.spring.implementation.dto.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public interface AppointmentPatientView {

    Instant getAppointmentDate();

    String getReasonForVisit();

    AppointmentStatus getStatus();

    // Doctor
    String getDoctorFullName();

    String getDoctorSpecialization();

    String getDoctorPhoneNumber();

    String getDoctorEmail();

    BigDecimal getConsultationFee();
}