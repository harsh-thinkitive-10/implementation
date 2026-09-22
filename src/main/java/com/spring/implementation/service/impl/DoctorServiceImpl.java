package com.spring.implementation.service.impl;

import com.spring.implementation.dto.*;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.dto.projection.DoctorDashboardView;
import com.spring.implementation.entity.DoctorEntity;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.repository.DoctorRepository;
import com.spring.implementation.service.DoctorService;
import com.spring.implementation.service.IamService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.ImagingOpException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    public Page<DoctorDTO> findAllDoctor(
            String search,
            String specialization,
            Boolean isActive,
            Pageable pageable
    ) {
        Page<DoctorEntity> doctors =
                doctorRepository.findDoctors(
                        search,
                        specialization,
                        isActive,
                        pageable
                );

        return doctors.map(DoctorEntity::toDto);
    }

    @Override
    @Transactional
    public DoctorDTO registerNewDoctor(RegisterDoctor doctorRequest) {

        CreateIamUserRequest iamRequest =
                RegisterDoctor.toRequest(doctorRequest);

        String keycloakUserId =
                iamService.createUser(iamRequest);

        DoctorEntity doctor = DoctorEntity.builder()
                .fullName(
                        doctorRequest.getFirstName()
                                + " "
                                + doctorRequest.getLastName()
                )
                .specialization(doctorRequest.getSpecialization())
                .phoneNumber(doctorRequest.getPhoneNumber())
                .email(doctorRequest.getEmail())
                .consultationFee(doctorRequest.getConsultationFee())
                .keycloakUserId(keycloakUserId)
                .build();

        DoctorEntity savedDoctor =
                doctorRepository.save(doctor);

        return DoctorEntity.toDto(savedDoctor);
    }

    @Override
    @Transactional
    public DoctorDTO updateDoctor(UUID uuid, DoctorDTO doctorDTO) throws ImplException {
        DoctorEntity doctor = doctorRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new ImplException(ResponseCode.NOT_FOUND,"Doctor not found"));

        doctor.setFullName(doctorDTO.getFullName());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setConsultationFee(doctorDTO.getConsultationFee());

        iamService.updateUser(
                doctor.getKeycloakUserId(),
                doctor.getFullName(),
                doctor.getEmail()
        );

        DoctorEntity updatedDoctor = doctorRepository.save(doctor);

        return DoctorEntity.toDto(updatedDoctor);
    }

    @Override
    @Transactional
    public void deleteDoctor(UUID uuid) throws ImplException {
        DoctorEntity doctor = doctorRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new ImplException(ResponseCode.NOT_FOUND,"Doctor not found"));

        doctor.setIsActive(false);
        doctorRepository.save(doctor);
    }
}
