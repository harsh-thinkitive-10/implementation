package com.spring.implementation.controller;

import com.spring.implementation.dto.DoctorDTO;
import com.spring.implementation.dto.DoctorProfileResponseDTO;
import com.spring.implementation.dto.DoctorProfileUpdateRequestDTO;
import com.spring.implementation.dto.Response;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.dto.projection.DoctorDashboardView;
import com.spring.implementation.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController extends AppController{

    private final DoctorService doctorService;

    @GetMapping("/dashboard")
    public ResponseEntity<Response> getDoctorDashboard() {

        DoctorDashboardView response =
                doctorService.getDoctorDashboard();

        return data(
                ResponseCode.OK,
                "Doctor dashboard fetched successfully",
                response
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<Response> getDoctorProfile() {

        DoctorProfileResponseDTO response =
                doctorService.getDoctorProfile();

        return data(
                ResponseCode.OK,
                "Doctor profile fetched successfully",
                response
        );
    }

    @PatchMapping("/profile")
    public ResponseEntity<Response> updateDoctorProfile(
            @Valid @RequestBody DoctorProfileUpdateRequestDTO request
    ) {

        DoctorProfileResponseDTO response =
                doctorService.updateDoctorProfile(request);

        return data(
                ResponseCode.OK,
                "Doctor profile updated successfully",
                response
        );
    }

    @GetMapping
    public ResponseEntity<Response> getAllDoctors() {

        return data(
                ResponseCode.OK,
                "Doctor list fetched successfully",
                doctorService.findAllDoctor()
        );
    }

}
