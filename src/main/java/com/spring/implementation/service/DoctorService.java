package com.spring.implementation.service;

import com.spring.implementation.dto.DoctorDTO;
import com.spring.implementation.dto.DoctorDashboardDTO;
import com.spring.implementation.dto.DoctorProfileResponseDTO;
import com.spring.implementation.dto.DoctorProfileUpdateRequestDTO;
import com.spring.implementation.dto.projection.DoctorDashboardView;

import java.util.List;


public interface DoctorService {

    List<DoctorDTO> findAllDoctor();


    DoctorDashboardView getDoctorDashboard();

    DoctorProfileResponseDTO getDoctorProfile();

    DoctorProfileResponseDTO updateDoctorProfile(DoctorProfileUpdateRequestDTO request);
}
