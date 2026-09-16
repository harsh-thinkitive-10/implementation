package com.spring.implementation.service.impl;

import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.DoctorDTO;
import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.dto.enums.AppointmentStatus;
import com.spring.implementation.dto.projection.AppointmentAdminView;
import com.spring.implementation.dto.projection.AppointmentDoctorView;
import com.spring.implementation.dto.projection.AppointmentPatientView;
import com.spring.implementation.entity.AppointmentEntity;
import com.spring.implementation.entity.DoctorEntity;
import com.spring.implementation.entity.PatientEntity;
import com.spring.implementation.repository.AppointmentRepository;
import com.spring.implementation.repository.DoctorRepository;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.AppointmentService;
import com.spring.implementation.service.DoctorService;
import com.spring.implementation.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public Page<AppointmentAdminView> findAllAppointment(Pageable pageable) {
        return appointmentRepository.findAllAppointments(pageable);
    }

    @Override
    public Page<AppointmentPatientView> findAppointmentsForPatient(Pageable pageable) {
        String keycloakUserId = getAuthenticatedUserId();
        return appointmentRepository.findAppointmentsForPatientByUserId(keycloakUserId,pageable);
    }

    @Override
    public Page<AppointmentDoctorView> findAppointmentsForDoctor(Pageable pageable) {
        String keycloakUserId = getAuthenticatedUserId();
        return appointmentRepository.findAppointmentsForDoctorByUserId(keycloakUserId,pageable);
    }

    @Override
    public AppointmentResponseDTO createNewAppointment(
            AppointmentRequestDTO appointmentRequestDTO
    ) {

        PatientEntity patient = patientRepository
                .findByUuid(appointmentRequestDTO.getPatientUuid())
                .orElseThrow(() ->
                        new RuntimeException("Patient not found")
                );

        DoctorEntity doctor = doctorRepository
                .findByUuid(appointmentRequestDTO.getDoctorUuid())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found")
                );

        AppointmentEntity appointment =
                AppointmentEntity.builder()
                        .appointmentDate(
                                appointmentRequestDTO.getAppointmentDate()
                        )
                        .reasonForVisit(
                                appointmentRequestDTO.getReasonForVisit()
                        )
                        .status(
                                appointmentRequestDTO.getStatus()
                        )
                        .patient(patient)
                        .doctor(doctor)
                        .build();

        AppointmentEntity savedAppointment =
                appointmentRepository.save(appointment);

        return new AppointmentResponseDTO(
                savedAppointment.getUuid(),
                savedAppointment.getAppointmentDate(),
                savedAppointment.getReasonForVisit(),
                savedAppointment.getStatus().name(),
                PatientEntity.toDTO(savedAppointment.getPatient()),
                DoctorEntity.toDto(savedAppointment.getDoctor())
        );
    }
    private String getAuthenticatedUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Authenticated user not found");
        }

        return authentication.getName();
    }
}
