package com.spring.implementation.controller;

import com.spring.implementation.dto.AllergyRequestDTO;
import com.spring.implementation.dto.Response;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.service.AllergyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/allergy")
@RequiredArgsConstructor
public class AllergyController extends AppController {

    private final AllergyService allergyService;

    @PostMapping
    public ResponseEntity<Response> create(@Valid @RequestBody AllergyRequestDTO request) {
        return data(ResponseCode.CREATED, "Allergy created successfully", allergyService.create(request));
    }

    @GetMapping("/patient/{patientUuid}")
    public ResponseEntity<Response> getByPatient(@PathVariable UUID patientUuid, @ParameterObject Pageable pageable) {
        return data(ResponseCode.OK, "Patient allergy list fetched successfully", allergyService.getByPatient(patientUuid, pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Response> getByUuid(@PathVariable UUID uuid) {
        return data(ResponseCode.OK, "Allergy fetched successfully", allergyService.getByUuid(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Response> update(@PathVariable UUID uuid, @Valid @RequestBody AllergyRequestDTO request) {

        return data(ResponseCode.OK, "Allergy updated successfully", allergyService.update(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Response> delete(@PathVariable UUID uuid) {
        allergyService.delete(uuid);
        return data(ResponseCode.OK, "Allergy deleted successfully", null);
    }
}