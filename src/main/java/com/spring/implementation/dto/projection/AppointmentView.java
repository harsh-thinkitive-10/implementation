package com.spring.implementation.dto.projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentView {

    LocalDate getAppointmentDate();

    LocalTime getAppointmentTime();

    String getReasonForVisit();

    String getStatus();

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
