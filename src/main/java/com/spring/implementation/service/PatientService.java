package com.spring.implementation.service;

import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.dto.PatientDashboardDTO;
import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.exception.ImplException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface PatientService {
    PatientDTO registerNewPatient(RegisterPatient patientRequest);

    Page<PatientDTO> getAllPatient(
            String search,
            String gender,
            Integer age,
            Pageable pageable
    );


    PatientDTO getPatientById(Long id);


    void deletePatient(Long id);

    PatientDTO GetKeyCloakId(String keyCloakUserId);

    String getKeycloakUserId(Long patientId);

    PatientDashboardDTO getMyDashboard(String keycloakUserId);

    PatientDTO updatePatient(UUID uuid, PatientDTO patientDTO) throws ImplException;

    void deletePatient(UUID uuid) throws ImplException;
}
