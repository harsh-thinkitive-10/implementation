package com.spring.implementation.service;

import com.spring.implementation.dto.DoctorDTO;
import com.spring.implementation.entity.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {

    List<DoctorDTO> findAllDoctor();

    DoctorDTO findDoctorById(Long id);

    void updateDoctorsConsultationFee(Long id,Double fees);

    void deleteDoctorById(Long id);
}
