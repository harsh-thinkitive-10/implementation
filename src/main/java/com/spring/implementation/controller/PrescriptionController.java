package com.spring.implementation.controller;

import com.spring.implementation.dto.PrescriptionRequestDTO;
import com.spring.implementation.dto.PrescriptionResponseDTO;
import com.spring.implementation.dto.Response;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prescription")
@RequiredArgsConstructor
public class PrescriptionController extends AppController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<Response> createPrescription(@Valid @RequestBody PrescriptionRequestDTO prescriptionRequestDTO) throws ImplException {
        PrescriptionResponseDTO response = prescriptionService.createPrescription(prescriptionRequestDTO);
        return data(ResponseCode.CREATED, "Prescription created successfully", response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Response> getPrescriptionByUuid(@PathVariable UUID uuid) throws ImplException {
        PrescriptionResponseDTO response = prescriptionService.getPrescriptionByUuid(uuid);
        return data(ResponseCode.OK, "Prescription fetched successfully", response);
    }

    @GetMapping("/appointment/{appointmentUuid}")
    public ResponseEntity<Response> getPrescriptionByAppointmentUuid(@PathVariable UUID appointmentUuid) throws ImplException {

        PrescriptionResponseDTO response = prescriptionService.getPrescriptionByAppointmentUuid(appointmentUuid);

        return data(ResponseCode.OK, "Prescription fetched successfully", response);
    }
}
