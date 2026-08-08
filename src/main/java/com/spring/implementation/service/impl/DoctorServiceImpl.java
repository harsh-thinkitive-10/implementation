package com.spring.implementation.service.impl;

import com.spring.implementation.dto.DoctorDTO;
import com.spring.implementation.entity.Doctor;
import com.spring.implementation.repository.DoctorRepository;
import com.spring.implementation.service.DoctorService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;

    @Override
    public List<DoctorDTO> findAllDoctor() {
        List<Doctor> doctors = doctorRepository.findAllDoctors();
        List<DoctorDTO> doctorDTOS = new ArrayList<>();
        doctors.forEach(doctor -> doctorDTOS.add(Doctor.toDto(doctor)));
        return doctorDTOS;
    }

    @Override
    public DoctorDTO findDoctorById(Long id){
        Doctor doctor = doctorRepository.findDoctorById(id);
        return Doctor.toDto(doctor);
    }

    @Override
    public void updateDoctorsConsultationFee(Long id, Double fees) {
        Optional<Doctor> doctor = Optional.ofNullable(doctorRepository.findDoctorById(id));
        doctor.ifPresent(doctor1 -> doctor1.setConsultationFee(fees));
    }

    @Override
    public void deleteDoctorById(Long id) {
        if(doctorRepository.existsById(id)) throw new RuntimeException("Doctor not found.");
        doctorRepository.deleteDoctorById(id);
    }


}
