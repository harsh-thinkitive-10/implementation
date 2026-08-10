package com.spring.implementation.service;


import com.spring.implementation.dto.AppointmentResponseDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentService {

    List<AppointmentResponseDTO> findAppointmentForPatientByPatientId(Long id);

}
