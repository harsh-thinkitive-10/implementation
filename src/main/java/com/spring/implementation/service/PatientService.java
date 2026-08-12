package com.spring.implementation.service;

import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.dto.RegisterPatient;

import java.util.List;


public interface PatientService {

    List<PatientDTO> getAllPatient();

    PatientDTO getPatientById(Long id);

    PatientDTO registerNewPatient(RegisterPatient patientRequest);

    PatientDTO updatePatientName(Long id,PatientDTO patientDTO);

    void deletePatient(Long id);

    PatientDTO GetKeyCloakId(String keyCloakUserId);

}
