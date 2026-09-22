package com.spring.implementation.controller;

import com.spring.implementation.dto.Response;
import com.spring.implementation.dto.TaxDTO;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.service.TaxService;
import com.spring.implementation.dto.enums.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tax")
@RequiredArgsConstructor
public class TaxController extends AppController {

    private final TaxService taxService;

    @PostMapping
    public ResponseEntity<Response> createTax(@Valid @RequestBody TaxDTO dto) {
        return data(ResponseCode.CREATED, "Tax ID created successfully", taxService.createTax(dto));
    }

    @GetMapping
    public ResponseEntity<Response> getAllTaxes(@RequestParam(required = false) String search, Pageable pageable) {
        return data(ResponseCode.OK, "Tax ID list fetched successfully", taxService.getAllTaxes(search, pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Response> getTax(@PathVariable UUID uuid) throws ImplException {
        return data(ResponseCode.OK, "Tax ID fetched successfully", taxService.getTaxByUuid(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Response> updateTax(@PathVariable UUID uuid, @Valid @RequestBody TaxDTO dto) throws ImplException {
        return data(ResponseCode.OK, "Tax ID updated successfully", taxService.updateTax(uuid, dto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteTax(@PathVariable UUID uuid) throws ImplException {
        taxService.deleteTax(uuid);
        return ResponseEntity.noContent().build();
    }
}