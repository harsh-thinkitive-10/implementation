package com.spring.implementation.service;

import com.spring.implementation.dto.DoctorDTO;
import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.dto.RegisterPatient;

public interface AuthService {

    String registerPatient(RegisterPatient patient);

    DoctorDTO registerDoctor();
}
