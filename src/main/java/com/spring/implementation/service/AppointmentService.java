package com.spring.implementation.service;


import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.projection.AppointmentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentService {

    Page<AppointmentView> findAllAppointment(Pageable pageable);

    List<AppointmentView> findAppointmentForPatientByPatientId(Long id);

    List<AppointmentView> findAppointmentForDoctorByDoctorId(Long id);

    void createNewAppointment(AppointmentRequestDTO appointmentRequestDTO);

    List<AppointmentView> findAppointmentsForPatient();

}
