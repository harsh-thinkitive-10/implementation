package com.spring.implementation.service;


import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.projection.AppointmentView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentService {

    List<AppointmentView> findAllAppointment();

    List<AppointmentView> findAppointmentForPatientByPatientId(Long id);

    List<AppointmentView> findAppointmentForDoctorByDoctorId(Long id);

    void createNewAppointment(AppointmentRequestDTO appointmentRequestDTO);



}
