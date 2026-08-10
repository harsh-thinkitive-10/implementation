package com.spring.implementation.service.impl;

import com.spring.implementation.dto.DoctorDTO;
import com.spring.implementation.entity.Doctor;
import com.spring.implementation.repository.DoctorRepository;
import com.spring.implementation.service.DoctorService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    public DoctorDTO updateDoctorsConsultationFee(Long id, DoctorDTO doctorDTO) {
       Doctor doctor = doctorRepository.findById(id).orElseThrow(()->new RuntimeException("Doctor not found"));
       if(doctorDTO.getFullName()!=null) doctor.setFullName(doctorDTO.getFullName());
       if(doctorDTO.getSpecialization()!=null) doctor.setSpecialization(doctorDTO.getSpecialization());
       if(doctorDTO.getPhoneNumber()!=null) doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
       if(doctorDTO.getEmail()!=null) doctor.setEmail(doctorDTO.getEmail());
       if(doctorDTO.getConsultationFee()!=null) doctor.setConsultationFee(doctorDTO.getConsultationFee());
       return Doctor.toDto(doctorRepository.save(doctor));
    }

    @Override
    public void deleteDoctorById(Long id) {
        if(doctorRepository.existsById(id)) throw new RuntimeException("Doctor not found.");
        doctorRepository.deleteDoctorById(id);
    }

}
