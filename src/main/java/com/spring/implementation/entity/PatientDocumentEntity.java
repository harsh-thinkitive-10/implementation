package com.spring.implementation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "patient_document")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDocumentEntity {

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

    private String documentType;

    private String fileType;

    @Column(nullable = false, length = 1000)
    private String fileUrl;

    private String uploadedBy;

    @PrePersist
    public void prePersist() {
        if (uuid == null) uuid = UUID.randomUUID();
    }
}