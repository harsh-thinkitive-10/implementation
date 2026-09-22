package com.spring.implementation.service.impl;

import com.spring.implementation.dto.LocationDTO;
import com.spring.implementation.dto.enums.ResponseCode;
import com.spring.implementation.entity.LocationEntity;
import com.spring.implementation.exception.ImplException;
import com.spring.implementation.repository.LocationRepository;
import com.spring.implementation.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public LocationDTO createLocation(LocationDTO dto) {
        if (locationRepository.existsByCodeIgnoreCaseAndIsActiveTrue(dto.getCode())) {
            throw new RuntimeException("Location code already exists");
        }

        LocationEntity location = LocationEntity.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .npi(dto.getNpi())
                .billingAddress(
                        com.spring.implementation.entity.AddressEntity.toEntity(
                                dto.getBillingAddress()))
                .build();

        return LocationEntity.toDto(locationRepository.save(location));
    }

    @Override
    public Page<LocationDTO> getAllLocations(String search, Pageable pageable) {
        return locationRepository.findLocations(search, pageable)
                .map(LocationEntity::toDto);
    }

    @Override
    public LocationDTO getLocationByUuid(UUID uuid) throws ImplException {
        return LocationEntity.toDto(
                locationRepository.findByUuidAndIsActiveTrue(uuid)
                        .orElseThrow(() -> new ImplException(ResponseCode.NOT_FOUND,"Location not found"))
        );
    }

    @Override
    @Transactional
    public LocationDTO updateLocation(UUID uuid, LocationDTO dto) throws ImplException {
        LocationEntity location = locationRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new ImplException(ResponseCode.NOT_FOUND,"Location not found"));

        location.setCode(dto.getCode());
        location.setName(dto.getName());
        location.setPhone(dto.getPhone());
        location.setEmail(dto.getEmail());
        location.setNpi(dto.getNpi());

        if (dto.getBillingAddress() != null) {
            location.setBillingAddress(
                    com.spring.implementation.entity.AddressEntity.toEntity(
                            dto.getBillingAddress()));
        }

        return LocationEntity.toDto(locationRepository.save(location));
    }

    @Override
    @Transactional
    public void deleteLocation(UUID uuid) throws ImplException {
        LocationEntity location = locationRepository.findByUuidAndIsActiveTrue(uuid)
                .orElseThrow(() -> new ImplException(ResponseCode.NOT_FOUND,"Location not found"));

        location.setIsActive(false);
        locationRepository.save(location);
    }
}