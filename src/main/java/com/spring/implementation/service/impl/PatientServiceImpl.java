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
    public List<PatientDTO> getAllStudent() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for(Patient patient : patients){
            patientDTOS.add(Patient.toDTO(patient));
        }
        return patientDTOS;
    }
}
