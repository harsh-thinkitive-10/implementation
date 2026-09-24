package com.spring.implementation.entity;

import com.spring.implementation.dto.AllergyRequestDTO;
import com.spring.implementation.dto.AllergyResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "allergy")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllergyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    @JdbcTypeCode(Types.CHAR)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @Column(nullable = false)
    private String name;

    private String allergen;

    private String reaction;

    private String severity;

    @Column(name = "onset_date")
    private Instant onsetDate;

    private String source;

    private String status;

    @Column(length = 1000)
    private String notes;

    @PrePersist
    public void prePersist() {
        if (uuid == null) uuid = UUID.randomUUID();
    }

    public AllergyResponseDTO toDTO() {
        return new AllergyResponseDTO(
                uuid,
                patient.getUuid(),
                name,
                allergen,
                reaction,
                severity,
                onsetDate,
                source,
                status,
                notes
        );
    }

    public static AllergyEntity toEntity(AllergyRequestDTO dto, PatientEntity patient) {
        return AllergyEntity.builder()
                .patient(patient)
                .name(dto.getName())
                .allergen(dto.getAllergen())
                .reaction(dto.getReaction())
                .severity(dto.getSeverity())
                .onsetDate(dto.getOnsetDate())
                .source(dto.getSource())
                .status(dto.getStatus())
                .notes(dto.getNotes())
                .build();
    }
}