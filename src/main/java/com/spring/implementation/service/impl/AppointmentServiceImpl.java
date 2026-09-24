package com.spring.implementation.service.impl;

import com.spring.implementation.dto.AppointmentFilterDTO;
import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.enums.AppointmentStatus;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.dto.projection.AppointmentAdminView;
import com.spring.implementation.dto.projection.AppointmentDoctorView;
import com.spring.implementation.dto.projection.AppointmentPatientView;
import com.spring.implementation.entity.*;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.repository.AppointmentRepository;
import com.spring.implementation.repository.DoctorRepository;
import com.spring.implementation.repository.LocationRepository;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.AppointmentService;
import com.spring.implementation.service.SlotService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final LocationRepository locationRepository;
    private final SlotService slotService;

    @Override
    public Page<AppointmentAdminView> getAllAppointments(AppointmentFilterDTO filter, Pageable pageable) {
        return appointmentRepository.findAllAppointments(pageable);
    }

    @Override
    public Page<AppointmentPatientView> getPatientAppointments(AppointmentFilterDTO filter, Pageable pageable) {
        String userId = getAuthenticatedUserId();

        return appointmentRepository.findAppointmentsForPatientByUserId(userId, pageable);
    }

    @Override
    public Page<AppointmentDoctorView> getDoctorAppointments(AppointmentFilterDTO filter, Pageable pageable) {
        String userId = getAuthenticatedUserId();

        return appointmentRepository.findAppointmentsForDoctorByUserId(userId, pageable);
    }

    @Override
    public List<AppointmentResponseDTO> getDoctorCalendar(UUID uuid, Instant start, Instant end) {
        return List.of();
    }

    @Override
    public AppointmentResponseDTO createNewAppointment(AppointmentRequestDTO request) throws ImplException {

        PatientEntity patient = patientRepository.findByUuid(request.getPatientUuid())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        DoctorEntity doctor = doctorRepository.findByUuid(request.getDoctorUuid())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        LocationEntity location = locationRepository.findByUuidAndIsActiveTrue(request.getLocationUuid())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Instant startTime = request.getAppointmentDate();
        Instant endTime = startTime.plus(30, ChronoUnit.MINUTES);

        if (!slotService.isSlotAvailable(
                request.getDoctorUuid(),
                request.getLocationUuid(),
                startTime
        )) {
            throw new ImplException(ResponseCode.CONFLICT,"Selected slot is no longer available");
        }

        AppointmentEntity appointment = AppointmentEntity.builder()
                .appointmentDate(startTime)
                .reasonForVisit(request.getReasonForVisit())
                .status(AppointmentStatus.SCHEDULED)
                .patient(patient)
                .doctor(doctor)
                .location(location)
                .build();

        AppointmentEntity saved = appointmentRepository.save(appointment);

        ZoneId zone = ZoneId.of("Asia/Kolkata");

        SlotEntity slot = SlotEntity.builder()
                .doctor(doctor)
                .location(location)
                .appointment(saved)
                .slotDate(startTime.atZone(zone).toLocalDate())
                .startTime(startTime)
                .endTime(endTime)
                .build();

        slotService.createSlot(slot);

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
    public void updateAppointmentStatus(UUID appointmentUuid, AppointmentStatus status) {
        AppointmentEntity appointment = appointmentRepository.findByUuid(appointmentUuid).orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(status);
    }

    private String getAuthenticatedUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Authenticated user not found");
        }

        return authentication.getName();
    }

}