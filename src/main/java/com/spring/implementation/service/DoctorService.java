package com.spring.implementation.service;

import com.spring.implementation.dto.*;
import com.spring.implementation.dto.projection.DoctorDashboardView;
import com.spring.implementation.exception.ImplException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface DoctorService {

    List<DoctorDTO> findAllDoctor();


    DoctorDashboardView getDoctorDashboard();

    DoctorProfileResponseDTO getDoctorProfile();

    DoctorProfileResponseDTO updateDoctorProfile(DoctorProfileUpdateRequestDTO request);

    Page<DoctorDTO> findAllDoctor(
            String search,
            String specialization,
            Boolean isActive,
            Pageable pageable
    );

    DoctorDTO registerNewDoctor(RegisterDoctor doctorRequest);

    DoctorDTO updateDoctor(UUID uuid, DoctorDTO doctorDTO) throws ImplException;

    void deleteDoctor(UUID uuid) throws ImplException;
}
