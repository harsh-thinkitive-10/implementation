package com.spring.implementation.controller;

import com.spring.implementation.dto.*;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.dto.projection.DoctorDashboardView;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController extends AppController {

    private final DoctorService doctorService;

    @GetMapping("/dashboard")
    public ResponseEntity<Response> getDoctorDashboard() {

        DoctorDashboardView response = doctorService.getDoctorDashboard();

        return data(ResponseCode.OK, "Doctor dashboard fetched successfully", response);
    }

    @GetMapping("/profile")
    public ResponseEntity<Response> getDoctorProfile() {

        DoctorProfileResponseDTO response = doctorService.getDoctorProfile();

        return data(ResponseCode.OK, "Doctor profile fetched successfully", response);
    }

    @PatchMapping("/profile")
    public ResponseEntity<Response> updateDoctorProfile(@Valid @RequestBody DoctorProfileUpdateRequestDTO request) {

        DoctorProfileResponseDTO response = doctorService.updateDoctorProfile(request);

        return data(ResponseCode.OK, "Doctor profile updated successfully", response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllDoctors(@RequestParam(required = false) String search, @RequestParam(required = false) String specialization, @RequestParam(required = false) Boolean isActive, Pageable pageable) {
        return data(ResponseCode.OK, "Doctor list fetched successfully", doctorService.findAllDoctor(search, specialization, isActive, pageable));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> registerNewDoctor(@Valid @RequestBody RegisterDoctor doctorRequest) {
        return data(ResponseCode.CREATED, "Doctor registered successfully", doctorService.registerNewDoctor(doctorRequest));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> updateDoctor(@PathVariable UUID uuid, @Valid @RequestBody DoctorDTO doctorDTO) throws ImplException {
        return data(ResponseCode.OK, "Doctor updated successfully", doctorService.updateDoctor(uuid, doctorDTO));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDoctor(@PathVariable UUID uuid) throws ImplException {
        doctorService.deleteDoctor(uuid);
        return ResponseEntity.noContent().build();
    }
}
