package com.spring.implementation.dto.projection;

import com.spring.implementation.dto.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentView {

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
