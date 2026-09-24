package com.spring.implementation.service;

import com.spring.implementation.dto.SlotResponseDTO;
import com.spring.implementation.entity.AppointmentEntity;
import com.spring.implementation.entity.DoctorEntity;
import com.spring.implementation.entity.LocationEntity;
import com.spring.implementation.entity.SlotEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SlotService {

    List<SlotResponseDTO> getAvailableSlots(
            UUID doctorUuid,
            UUID locationUuid,
            Instant date
    );

        SlotEntity createSlot(
                SlotEntity slot
        );
    boolean isSlotAvailable(UUID doctorUuid, UUID locationUuid, Instant startTime);
}