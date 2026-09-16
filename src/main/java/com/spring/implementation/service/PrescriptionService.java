package com.spring.implementation.service;

import com.spring.implementation.dto.PrescriptionRequestDTO;
import com.spring.implementation.dto.PrescriptionResponseDTO;
import com.spring.implementation.exception.ImplException;


import java.util.UUID;


public interface PrescriptionService {

    PrescriptionResponseDTO createPrescription(
            PrescriptionRequestDTO prescriptionRequestDTO
    ) throws ImplException;

    PrescriptionResponseDTO getPrescriptionByUuid(
            UUID uuid
    ) throws ImplException;

    PrescriptionResponseDTO getPrescriptionByAppointmentUuid(
            UUID appointmentUuid
    ) throws ImplException;

}
