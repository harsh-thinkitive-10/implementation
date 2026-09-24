package com.spring.implementation.service.impl;

import com.spring.implementation.dto.SlotResponseDTO;
import com.spring.implementation.entity.SlotEntity;
import com.spring.implementation.repository.SlotRepository;
import com.spring.implementation.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {

    private final SlotRepository slotRepository;

    @Override
    public List<SlotResponseDTO> getAvailableSlots(UUID doctorUuid, UUID locationUuid, Instant date) {

        Instant dayStart = date.atZone(ZoneOffset.UTC)
                .withHour(9)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .toInstant();

        Instant dayEnd = date.atZone(ZoneOffset.UTC)
                .withHour(17)
                .toInstant();

        Set<Instant> bookedTimes = slotRepository.findBookedSlots(doctorUuid, locationUuid, dayStart, dayEnd).stream().map(SlotEntity::getStartTime).collect(Collectors.toSet());

        return IntStream.range(0, 16).mapToObj(i -> {
            Instant start = dayStart.plus(i * 30L, ChronoUnit.MINUTES);
            return new SlotResponseDTO(start, start.plus(30, ChronoUnit.MINUTES));
        }).filter(slot -> !bookedTimes.contains(slot.getStartTime())).toList();
    }

    @Override
    public SlotEntity createSlot(SlotEntity slot) {
        return slotRepository.save(slot);
    }

    @Override
    public boolean isSlotAvailable(UUID doctorUuid, UUID locationUuid, Instant startTime) {

        Instant endTime = startTime.plus(30, ChronoUnit.MINUTES);

        return slotRepository
                .findBookedSlots(doctorUuid, locationUuid, startTime, endTime)
                .isEmpty();
    }
}