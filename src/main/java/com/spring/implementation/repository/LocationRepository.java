package com.spring.implementation.repository;

import com.spring.implementation.entity.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findByUuid(UUID uuid);

    Optional<LocationEntity> findByUuidAndIsActiveTrue(UUID uuid);

    boolean existsByCodeIgnoreCaseAndIsActiveTrue(String code);

    boolean existsByNpiAndIsActiveTrue(String npi);

    @Query("""
        SELECT l
        FROM LocationEntity l
        WHERE l.isActive = true
        AND (
            :search IS NULL
            OR :search = ''
            OR LOWER(l.code) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(l.name) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(l.email) LIKE LOWER(CONCAT('%', :search, '%'))
            OR l.phone LIKE CONCAT('%', :search, '%')
            OR l.npi LIKE CONCAT('%', :search, '%')
        )
        """)
    Page<LocationEntity> findLocations(
            @Param("search") String search,
            Pageable pageable
    );
}