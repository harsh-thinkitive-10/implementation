package com.spring.implementation.controller;

import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.Response;
import com.spring.implementation.dto.projection.AppointmentView;
import com.spring.implementation.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController extends AppController{

    private final AppointmentService appointmentService;

    @GetMapping("/admin")
    public ResponseEntity<Response> getAllAppointments(@ParameterObject Pageable pageable) {
        return data(HttpStatus.OK, "Appointment list fetched successfully", appointmentService.findAllAppointment(pageable));
    }


    //@PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patient/{id}")
    public ResponseEntity<List<AppointmentView>> getAllAppointmentsByPatientId(@PathVariable Long id) {
        List<AppointmentView> appointmentView = appointmentService.findAppointmentForPatientByPatientId(id);
        return ResponseEntity.ok(appointmentView);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<AppointmentView>> getAllAppointmentsByDoctorId(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(appointmentService.findAppointmentForDoctorByDoctorId(id)));
    }

    @PostMapping()
    public ResponseEntity<Void> createNewAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        appointmentService.createNewAppointment(appointmentRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@RequestBody String value) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteAppointment(String id) {

            return ResponseEntity.noContent().build();
    }

}
