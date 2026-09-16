package com.spring.implementation.service.impl;

import com.spring.implementation.dto.PrescriptionRequestDTO;
import com.spring.implementation.dto.PrescriptionResponseDTO;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.entity.AppointmentEntity;
import com.spring.implementation.entity.PrescriptionEntity;
import com.spring.implementation.exception.AppointmentNotFound;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.repository.AppointmentRepository;
import com.spring.implementation.repository.PrescriptionRepository;
import com.spring.implementation.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO prescriptionRequestDTO) throws ImplException {
        Optional<AppointmentEntity> appointment = Optional.of(
                appointmentRepository.findByUuid(
                        prescriptionRequestDTO.getAppointmentUuid()).orElseThrow(()->
                        new AppointmentNotFound("Appointment with ID : "+prescriptionRequestDTO.getAppointmentUuid()
                        )
                )
        );

        String keycloakUserId = getAuthenticatedUserId();

        if (!appointment.get().getDoctor().getKeycloakUserId().equals(keycloakUserId)) {
            throw new AccessDeniedException("You are not authorized to create a prescription for this appointment");
        }

        if (prescriptionRepository.existsByAppointment(appointment.get())) {
            throw new ImplException(ResponseCode.NOT_FOUND,"Prescription already exists for this appointment");
        }
        PrescriptionEntity prescription = PrescriptionEntity.toEntity(prescriptionRequestDTO,appointment.get());

        PrescriptionEntity savedPrescription = prescriptionRepository.save(prescription);

        return PrescriptionEntity.toDTO(savedPrescription);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponseDTO getPrescriptionByUuid(UUID uuid) throws ImplException {
        Optional<PrescriptionEntity> prescriptionEntity = Optional.of(prescriptionRepository.findByUuid(uuid).orElseThrow(
                () -> new ImplException(ResponseCode.NOT_FOUND, "Prescription not found")));
        return PrescriptionEntity.toDTO(prescriptionEntity.get());
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponseDTO getPrescriptionByAppointmentUuid(UUID appointmentUuid) throws ImplException {
        Optional<PrescriptionEntity> prescriptionEntity = Optional.of(prescriptionRepository.findByAppointmentUuid(appointmentUuid).orElseThrow(
                () -> new ImplException(ResponseCode.NOT_FOUND, "Prescription not found")));
        return PrescriptionEntity.toDTO(prescriptionEntity.get());
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
