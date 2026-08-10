package com.spring.implementation.service.impl;

import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.entity.Patient;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;


    @Override
    @Cacheable(cacheNames = "get patient")
    public List<PatientDTO> getAllPatient() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for(Patient patient : patients){
            patientDTOS.add(Patient.toDTO(patient));
        }
        return patientDTOS;
    }

    @Override
    @Transactional()
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found."));
        patient.setFullName("Bablu");
        return Patient.toDTO(patient);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public PatientDTO addNewPatient(@NonNull PatientDTO patientDTO) {
        Patient patient = Patient.builder()
                .fullName(patientDTO.getFullName())
                .age(patientDTO.getAge())
                .gender(patientDTO.getGender())
                .phoneNumber(patientDTO.getPhoneNumber())
                .email(patientDTO.getEmail())
                .build();
        return Patient.toDTO(patientRepository.save(patient));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public PatientDTO updatePatientName(Long id,PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found."));
        if(patientDTO.getFullName()!=null) patient.setFullName(patientDTO.getFullName());
        if(patientDTO.getGender()!=null) patient.setGender(patientDTO.getGender());
        if(patientDTO.getEmail()!=null) patient.setEmail(patientDTO.getEmail());
        if(patientDTO.getAge()!=null) patient.setAge(patientDTO.getAge());
        if(patientDTO.getPhoneNumber()!=null) patient.setPhoneNumber(patientDTO.getPhoneNumber());
        return Patient.toDTO(patientRepository.save(patient));
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found."));
        patientRepository.delete(patient);
    }
}
