package com.spring.implementation.service;

import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.PatientDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PatientService {

    List<PatientDTO> getAllPatient();

    PatientDTO getPatientById(Long id);

    PatientDTO addNewPatient(PatientDTO patientDTO);

    PatientDTO updatePatientName(Long id,PatientDTO patientDTO);

    void deletePatient(Long id);

}
