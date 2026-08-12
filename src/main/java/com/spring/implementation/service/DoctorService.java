package com.spring.implementation.service;

import com.spring.implementation.dto.DoctorDTO;

import java.util.List;


public interface DoctorService {

    List<DoctorDTO> findAllDoctor();

    DoctorDTO findDoctorById(Long id);

    DoctorDTO updateDoctorsConsultationFee(Long id,DoctorDTO doctorDTO);

    void deleteDoctorById(Long id);
}
