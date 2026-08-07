package com.spring.implementation.service;

import com.spring.implementation.dto.PatientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {

    List<PatientDTO> getAllStudent();
}
