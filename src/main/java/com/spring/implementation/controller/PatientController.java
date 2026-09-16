package com.spring.implementation.controller;

import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.dto.PatientDashboardDTO;
import com.spring.implementation.dto.RegisterPatient;
import com.spring.implementation.dto.Response;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.service.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
public class PatientController extends AppController{


    private final PatientService patientService;

    @GetMapping("/me")
    public ResponseEntity<PatientDTO> getMyProfile(@AuthenticationPrincipal Jwt jwt) {
        String keyCloakUserId = jwt.getSubject();
        return ResponseEntity.ok(patientService.GetKeyCloakId(keyCloakUserId));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<PatientDashboardDTO> getDashboard(
            @AuthenticationPrincipal Jwt jwt
    ) {

        return ResponseEntity.ok(
                patientService.getMyDashboard(
                        jwt.getSubject()
                )
        );
    }


    @GetMapping("/patients")
    public ResponseEntity<Response> get() {

        return data(
                ResponseCode.OK,
                "Patient list fetched successfully",
                patientService.getAllPatient()
        );
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

    @PatchMapping("/me")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody @Validated PatientDTO patientDTO, @AuthenticationPrincipal Jwt jwt ){
        return ResponseEntity.ok(patientService.updatePatient(jwt.getSubject(), patientDTO));
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
