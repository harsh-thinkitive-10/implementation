package com.spring.implementation.repository;

import com.spring.implementation.entity.TaxEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TaxRepository extends JpaRepository<TaxEntity, Long> {

    Optional<TaxEntity> findByUuidAndIsActiveTrue(UUID uuid);

    boolean existsByTaxIdIgnoreCaseAndIsActiveTrue(String taxId);

    @Query("""
        SELECT t
        FROM TaxEntity t
        WHERE t.isActive = true
        AND (
            :search IS NULL
            OR :search = ''
            OR LOWER(t.taxId) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(t.npi) LIKE LOWER(CONCAT('%', :search, '%'))
        )
        """)
    Page<TaxEntity> findTaxes(
            @Param("search") String search,
            Pageable pageable
    );
}