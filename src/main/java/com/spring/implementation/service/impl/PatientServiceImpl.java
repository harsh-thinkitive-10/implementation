package com.spring.implementation.service.impl;

import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.entity.Patient;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.PatientService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    @Override
    public List<PatientDTO> getAllPatient() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for(Patient patient : patients){
            patientDTOS.add(Patient.toDTO(patient));
        }
        return patientDTOS;
    }

    @Override
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found."));
        return Patient.toDTO(patient);
    }

    @Override
    public void addNewPatient(PatientDTO patientDTO) {
        Patient patient = Patient.builder()
                .fullName(patientDTO.getFullName())
                .age(patientDTO.getAge())
                .gender(patientDTO.getGender())
                .phoneNumber(patientDTO.getPhoneNumber())
                .email(patientDTO.getEmail())
                .build();
        patientRepository.save(patient);
    }

    @Override
    public PatientDTO updatePatientName(Long id, String name) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found."));
        patient.setFullName(name);
        return Patient.toDTO(patient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new RuntimeException("Patient not found."));
        patientRepository.delete(patient);
    }


}
