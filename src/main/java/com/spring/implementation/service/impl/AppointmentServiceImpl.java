package com.spring.implementation.service.impl;

import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.entity.Appointment;
import com.spring.implementation.repository.AppointmentRepository;
import com.spring.implementation.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<AppointmentResponseDTO> findAppointmentForPatientByPatientId(Long id) {
       List<Appointment> appointmentResponseDTO = appointmentRepository.findAppointmentByPatientPatientId(id);
        System.out.println(appointmentResponseDTO);
       return List.of();
    }
}
