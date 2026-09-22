package com.spring.implementation.service;

import com.spring.implementation.dto.LocationDTO;
import com.spring.implementation.exception.ImplException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LocationService {

    LocationDTO createLocation(LocationDTO locationDTO);

    Page<LocationDTO> getAllLocations(
            String search,
            Pageable pageable
    );

    LocationDTO getLocationByUuid(UUID uuid) throws ImplException;

    LocationDTO updateLocation(
            UUID uuid,
            LocationDTO locationDTO
    ) throws ImplException;

    void deleteLocation(UUID uuid) throws ImplException;
}