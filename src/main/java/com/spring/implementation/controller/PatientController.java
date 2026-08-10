package com.spring.implementation.controller;

import com.spring.implementation.dto.PatientDTO;
import com.spring.implementation.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PatientController {


    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> get() {
        return ResponseEntity.ok(patientService.getAllPatient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        log.info("User id:{} id is calling api:", id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping()
    public ResponseEntity<PatientDTO> addNewPatient(@RequestBody @Valid PatientDTO patientDTO) {
        return ResponseEntity.ok(patientService.addNewPatient(patientDTO));
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
