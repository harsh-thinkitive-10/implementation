package com.spring.implementation.service;


import com.spring.implementation.dto.AppointmentFilterDTO;
import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.enums.AppointmentStatus;
import com.spring.implementation.dto.projection.AppointmentAdminView;
import com.spring.implementation.dto.projection.AppointmentDoctorView;
import com.spring.implementation.dto.projection.AppointmentPatientView;
import com.spring.implementation.exception.ImplException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    AppointmentResponseDTO createNewAppointment(AppointmentRequestDTO appointmentRequestDTO) throws ImplException;

    void updateAppointmentStatus(UUID appointmentUuid, AppointmentStatus status);

    Page<AppointmentAdminView> getAllAppointments(AppointmentFilterDTO filter, Pageable pageable);

    Page<AppointmentPatientView> getPatientAppointments(AppointmentFilterDTO filter, Pageable pageable);

    Page<AppointmentDoctorView> getDoctorAppointments(AppointmentFilterDTO filter, Pageable pageable);

    List<AppointmentResponseDTO> getDoctorCalendar(UUID uuid, Instant start, Instant end);

}
