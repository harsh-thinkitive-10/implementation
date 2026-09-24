package com.spring.implementation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "vital")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VitalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    @JdbcTypeCode(Types.CHAR)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    private String systolic;
    private String diastolic;
    private String bloodPressurePosition;

    private String temperature;
    private String temperatureUnit;
    private String temperatureRoute;

    private String heartRate;
    private String heartRateRhythm;
    private String respiratoryRate;

    @Column(name = "spo2")
    private String spo2;

    private Boolean onRoomAir;

    private String weight;
    private String weightUnit;

    private String heightFeet;
    private String heightInches;
    private String bmi;

    @Column(name = "vision_od")
    private String visionOD;

    @Column(name = "vision_os")
    private String visionOS;
    private String visionCorrected;

    private String hearingRight;
    private String hearingLeft;
    private String hearingAid;

    private String gait;
    private String assistiveDevice;

    @PrePersist
    public void prePersist() {
        if (uuid == null) uuid = UUID.randomUUID();
    }
}