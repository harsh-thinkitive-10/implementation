package com.spring.implementation.service.impl;

import com.spring.implementation.dto.DoctorDTO;
import com.spring.implementation.dto.DoctorDashboardDTO;
import com.spring.implementation.dto.DoctorProfileResponseDTO;
import com.spring.implementation.dto.DoctorProfileUpdateRequestDTO;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.dto.projection.DoctorDashboardView;
import com.spring.implementation.entity.DoctorEntity;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.repository.DoctorRepository;
import com.spring.implementation.service.DoctorService;
import com.spring.implementation.service.IamService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.ImagingOpException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;
    private final IamService iamService;

    @Override
    public List<DoctorDTO> findAllDoctor() {
        List<DoctorEntity> doctorEntities = doctorRepository.findAllDoctors();
        List<DoctorDTO> doctorDTOS = new ArrayList<>();
        doctorEntities.forEach(doctor -> doctorDTOS.add(DoctorEntity.toDto(doctor)));
        return doctorDTOS;
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorDashboardView getDoctorDashboard() {

        String keycloakUserId = getAuthenticatedUserId();

        return doctorRepository.getDoctorDashboard(keycloakUserId);
    }

    private String getAuthenticatedUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Authenticated user not found");
        }

        return authentication.getName();
    }
    @Override
    @Transactional(readOnly = true)
    public DoctorProfileResponseDTO getDoctorProfile()  {

        String keycloakUserId = getAuthenticatedUserId();

        DoctorEntity doctor = doctorRepository
                .findByKeycloakUserId(keycloakUserId)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found")
                );

        return DoctorProfileResponseDTO.builder()
                .uuid(doctor.getUuid())
                .fullName(doctor.getFullName())
                .specialization(doctor.getSpecialization())
                .phoneNumber(doctor.getPhoneNumber())
                .email(doctor.getEmail())
                .consultationFee(doctor.getConsultationFee())
                .build();
    }

    @Override
    @Transactional
    public DoctorProfileResponseDTO updateDoctorProfile(
            DoctorProfileUpdateRequestDTO request
    ) {

        String keycloakUserId = getAuthenticatedUserId();

        DoctorEntity doctor = doctorRepository
                .findByKeycloakUserId(keycloakUserId)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found")
                );

        doctor.setFullName(request.getFullName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setEmail(request.getEmail());
        doctor.setConsultationFee(request.getConsultationFee());

        DoctorEntity updatedDoctor =
                doctorRepository.save(doctor);

        iamService.updateUser(
                keycloakUserId,
                request.getFullName(),
                request.getEmail()
        );

        return DoctorProfileResponseDTO.builder()
                .uuid(updatedDoctor.getUuid())
                .fullName(updatedDoctor.getFullName())
                .specialization(updatedDoctor.getSpecialization())
                .phoneNumber(updatedDoctor.getPhoneNumber())
                .email(updatedDoctor.getEmail())
                .consultationFee(updatedDoctor.getConsultationFee())
                .build();
    }
}
