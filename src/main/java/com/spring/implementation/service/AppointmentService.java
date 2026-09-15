package com.spring.implementation.service;


import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.projection.AppointmentAdminView;
import com.spring.implementation.dto.projection.AppointmentDoctorView;
import com.spring.implementation.dto.projection.AppointmentPatientView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    Page<AppointmentAdminView> findAllAppointment(Pageable pageable);

    Page<AppointmentPatientView> findAppointmentsForPatient(Pageable pageable);

    Page<AppointmentDoctorView> findAppointmentsForDoctor(Pageable pageable);

    void createNewAppointment(AppointmentRequestDTO appointmentRequestDTO);

}
