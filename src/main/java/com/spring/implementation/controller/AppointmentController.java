package com.spring.implementation.controller;

import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.Response;
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
        return data(HttpStatus.OK, "Appointment list fetched successfully", appointmentService.findAllAppointment(pageable));
    }

    @GetMapping("/doctor")
    public ResponseEntity<Response> getDoctorAppointments(@ParameterObject Pageable pageable) {
        return data(HttpStatus.OK, "Doctor appointment list fetched successfully", appointmentService.findAppointmentsForDoctor(pageable));
    }

    @GetMapping("/patient")
    public ResponseEntity<Response> getPatientAppointments(@ParameterObject Pageable pageable) {
        return data(HttpStatus.OK, "Patient appointment list fetched successfully", appointmentService.findAppointmentsForPatient(pageable));
    }

    @PostMapping
    public ResponseEntity<Void> createNewAppointment(@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        appointmentService.createNewAppointment(appointmentRequestDTO);
        return ResponseEntity.noContent().build();
    }
}