package com.spring.implementation.controller;

import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.Response;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;


@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController extends AppController {

    private final AppointmentService appointmentService;

    @GetMapping("/admin")
    public ResponseEntity<Response> getAllAppointments(@ParameterObject Pageable pageable) {
        return data(ResponseCode.OK, "Appointment list fetched successfully", appointmentService.findAllAppointment(pageable));
    }

    @GetMapping("/doctor")
    public ResponseEntity<Response> getDoctorAppointments(@ParameterObject Pageable pageable) {
        return data(ResponseCode.OK, "Doctor appointment list fetched successfully", appointmentService.findAppointmentsForDoctor(pageable));
    }

    @GetMapping("/patient")
    public ResponseEntity<Response> getPatientAppointments(@ParameterObject Pageable pageable) {
        return data(ResponseCode.OK, "Patient appointment list fetched successfully", appointmentService.findAppointmentsForPatient(pageable));
    }

    @PostMapping
    public ResponseEntity<Response> createNewAppointment(
            @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO
    ) {

        AppointmentResponseDTO response =
                appointmentService.createNewAppointment(
                        appointmentRequestDTO
                );

        return data(
                ResponseCode.CREATED,
                "Appointment created successfully",
                response
        );
    }
}