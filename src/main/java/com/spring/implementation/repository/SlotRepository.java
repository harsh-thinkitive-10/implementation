package com.spring.implementation.repository;

import com.spring.implementation.entity.SlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SlotRepository extends JpaRepository<SlotEntity, Long> {

    @Query("""
    SELECT s FROM SlotEntity s
    WHERE (s.doctor.uuid = :doctorUuid OR s.location.uuid = :locationUuid)
    AND s.startTime < :dayEnd
    AND s.endTime > :dayStart
""")
    List<SlotEntity> findBookedSlots(
            UUID doctorUuid,
            UUID locationUuid,
            Instant dayStart,
            Instant dayEnd
    );
}