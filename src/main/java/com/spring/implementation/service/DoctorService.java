package com.spring.implementation.service;

import com.spring.implementation.dto.DoctorDTO;
import com.spring.implementation.entity.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {

    List<DoctorDTO> findAllDoctor();

    DoctorDTO findDoctorById(Long id);

    DoctorDTO updateDoctorsConsultationFee(Long id,DoctorDTO doctorDTO);

    void deleteDoctorById(Long id);
}
