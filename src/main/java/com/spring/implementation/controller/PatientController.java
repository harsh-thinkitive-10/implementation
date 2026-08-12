package com.spring.implementation.controller;

import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PatientController {


    private final PatientService patientService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<PatientDTO> getMyProfile(@AuthenticationPrincipal Jwt jwt) {
        String keyCloakUserId = jwt.getSubject();
        return ResponseEntity.ok(patientService.GetKeyCloakId(keyCloakUserId));
    }

    @PreAuthorize("hasRole('ADMIIN')")
    @GetMapping("/patients")
    public ResponseEntity<List<PatientDTO>> get() {
        return ResponseEntity.ok(patientService.getAllPatient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        log.info("User id:{} id is calling api:", id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<PatientDTO> registerNewPatient(@RequestBody @Valid RegisterPatient patientRequest) {
        return ResponseEntity.ok(patientService.registerNewPatient(patientRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientDTO> patch(@RequestBody @Validated PatientDTO patientDTO, @PathVariable Long id) {
        return ResponseEntity.ok(patientService.updatePatientName(id,patientDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> changePatient(@PathVariable Long id,@RequestBody PatientDTO patientDTO) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
