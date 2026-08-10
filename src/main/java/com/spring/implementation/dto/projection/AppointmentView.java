package com.spring.implementation.dto.projection;

import java.time.LocalDate;

public interface AppointmentView {

    String getPatientName();

    String getDoctorName();

    LocalDate getAppointmentDate();
}
