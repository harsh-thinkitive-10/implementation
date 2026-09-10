package com.spring.implementation.service.impl;

import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.projection.AppointmentView;
import com.spring.implementation.entity.AppointmentEntity;
import com.spring.implementation.entity.DoctorEntity;
import com.spring.implementation.entity.PatientEntity;
import com.spring.implementation.repository.AppointmentRepository;
import com.spring.implementation.service.AppointmentService;
import com.spring.implementation.service.DoctorService;
import com.spring.implementation.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;


    @Override
    public Page<AppointmentView> findAllAppointment(Pageable pageable) {
        return appointmentRepository.findAllAppointment(pageable);
    }

    @Override
    public List<AppointmentView> findAppointmentForPatientByPatientId(Long id) {
       return appointmentRepository.findAppointmentForPatientByPatientId(id);
    }

    @Override
    public List<AppointmentView> findAppointmentForDoctorByDoctorId(Long id) {
        return appointmentRepository.findAppointmentForDoctorById(id);
    }

    @Override
    public void createNewAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        AppointmentEntity appointment = AppointmentEntity.builder()
                .appointmentDate(appointmentRequestDTO.getAppointmentDate())
                .reasonForVisit(appointmentRequestDTO.getReasonForVisit())
                .status(appointmentRequestDTO.getStatus())
                .patient(PatientEntity.toEntity(patientService.getPatientById(appointmentRequestDTO.getPatientId())))
                .doctor(DoctorEntity.toEntity(doctorService.findDoctorById(appointmentRequestDTO.getDoctorId())))
                .build();

        appointmentRepository.save(appointment);

        }

    @Override
    public List<AppointmentView> findAppointmentsForPatient() {
        return List.of();
    }

}
