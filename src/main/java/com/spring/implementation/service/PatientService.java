package com.spring.implementation.service;

import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.PatientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {

    List<PatientDTO> getAllPatient();

    PatientDTO getPatientById(Long id);

    void addNewPatient(PatientDTO patientDTO);

    PatientDTO updatePatientName(Long id,String name);

    void deletePatient(Long id);

}
