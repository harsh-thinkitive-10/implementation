package com.spring.implementation.controller;

import com.spring.implementation.dto.LocationDTO;
import com.spring.implementation.dto.Response;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.service.LocationService;
import com.spring.implementation.dto.enums.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController extends AppController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Response> createLocation(@Valid @RequestBody LocationDTO dto) {
        return data(ResponseCode.CREATED, "Location created successfully", locationService.createLocation(dto));
    }

    @GetMapping
    public ResponseEntity<Response> getAllLocations(@RequestParam(required = false) String search, @ParameterObject Pageable pageable) {
        return data(ResponseCode.OK, "Location list fetched successfully", locationService.getAllLocations(search, pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Response> getLocation(@PathVariable UUID uuid) throws ImplException {
        return data(ResponseCode.OK, "Location fetched successfully", locationService.getLocationByUuid(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Response> updateLocation(@PathVariable UUID uuid, @Valid @RequestBody LocationDTO dto) throws ImplException {
        return data(ResponseCode.OK, "Location updated successfully", locationService.updateLocation(uuid, dto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID uuid) throws ImplException {
        locationService.deleteLocation(uuid);
        return ResponseEntity.noContent().build();
    }
}