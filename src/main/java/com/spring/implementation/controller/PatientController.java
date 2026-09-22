package com.spring.implementation.controller;

import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.dto.PatientDashboardDTO;
import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.dto.Response;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.service.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PatientController extends AppController {


    private final PatientService patientService;

    @GetMapping("/me")
    public ResponseEntity<PatientDTO> getMyProfile(@AuthenticationPrincipal Jwt jwt) {
        String keyCloakUserId = jwt.getSubject();
        return ResponseEntity.ok(patientService.GetKeyCloakId(keyCloakUserId));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<PatientDashboardDTO> getDashboard(@AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(patientService.getMyDashboard(jwt.getSubject()));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> registerNewPatient(@RequestBody @Valid RegisterPatient patientRequest) {
        return data(ResponseCode.CREATED, "Patient registered successfully", patientService.registerNewPatient(patientRequest));
    }

    @GetMapping("/patients")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getAllPatients(@RequestParam(required = false) String search, @RequestParam(required = false) String gender, @RequestParam(required = false) Integer age, Pageable pageable) {
        return data(ResponseCode.OK, "Patient list fetched successfully", patientService.getAllPatient(search, gender, age, pageable));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> updatePatient(@PathVariable UUID uuid, @Valid @RequestBody PatientDTO patientDTO) throws ImplException {
        return data(ResponseCode.OK, "Patient updated successfully", patientService.updatePatient(uuid, patientDTO));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deletePatient(@PathVariable UUID uuid) throws ImplException {
        patientService.deletePatient(uuid);
        return data(ResponseCode.OK, "Patient Deleted Successfully", null);
    }
}
