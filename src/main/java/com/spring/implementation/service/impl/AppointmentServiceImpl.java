package com.spring.implementation.service.impl;

import com.spring.implementation.dto.AppointmentFilterDTO;
import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.enums.AppointmentStatus;
import com.spring.implementation.dto.projection.AppointmentAdminView;
import com.spring.implementation.dto.projection.AppointmentDoctorView;
import com.spring.implementation.dto.projection.AppointmentPatientView;
import com.spring.implementation.entity.AppointmentEntity;
import com.spring.implementation.entity.DoctorEntity;
import com.spring.implementation.entity.LocationEntity;
import com.spring.implementation.entity.PatientEntity;
import com.spring.implementation.repository.AppointmentRepository;
import com.spring.implementation.repository.DoctorRepository;
import com.spring.implementation.repository.LocationRepository;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final LocationRepository locationRepository;

    @Override
    public Page<AppointmentAdminView> getAllAppointments(
            AppointmentFilterDTO filter,
            Pageable pageable
    ) {
        return appointmentRepository.findAllAppointments(pageable);
    }

    @Override
    public Page<AppointmentPatientView> getPatientAppointments(
            AppointmentFilterDTO filter,
            Pageable pageable
    ) {
        String userId = getAuthenticatedUserId();

        return appointmentRepository
                .findAppointmentsForPatientByUserId(userId, pageable);
    }

    @Override
    public Page<AppointmentDoctorView> getDoctorAppointments(
            AppointmentFilterDTO filter,
            Pageable pageable
    ) {
        String userId = getAuthenticatedUserId();

        return appointmentRepository
                .findAppointmentsForDoctorByUserId(userId, pageable);
    }

    @Override
    public AppointmentResponseDTO createNewAppointment(
            AppointmentRequestDTO request
    ) {

        PatientEntity patient = patientRepository
                .findByUuid(request.getPatientUuid())
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        DoctorEntity doctor = doctorRepository
                .findByUuid(request.getDoctorUuid())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        LocationEntity location = locationRepository
                .findByUuidAndIsActiveTrue(request.getLocationUuid())
                .orElseThrow(() ->
                        new RuntimeException("Location not found"));

        AppointmentEntity appointment = AppointmentEntity.builder()
                .appointmentDate(request.getAppointmentDate())
                .reasonForVisit(request.getReasonForVisit())
                .status(request.getStatus())
                .patient(patient)
                .doctor(doctor)
                .location(location)
                .build();

        AppointmentEntity saved = appointmentRepository.save(appointment);

        return new AppointmentResponseDTO(
                saved.getUuid(),
                saved.getAppointmentDate(),
                saved.getReasonForVisit(),
                saved.getStatus().name(),
                PatientEntity.toDTO(saved.getPatient()),
                DoctorEntity.toDto(saved.getDoctor()),
                LocationEntity.toDto(saved.getLocation())
        );
    }

    @Override
    public void updateAppointmentStatus(
            UUID appointmentUuid,
            AppointmentStatus status
    ) {
        AppointmentEntity appointment = appointmentRepository
                .findByUuid(appointmentUuid)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));

        appointment.setStatus(status);
    }

    private String getAuthenticatedUserId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Authenticated user not found");
        }

        return authentication.getName();
    }
}