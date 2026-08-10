package com.spring.implementation.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Table(name = "prescription")
@Entity
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;
    private String medicineName;
    private String dosage;
    private String frequency;
    private Integer duration;
    private String instructions;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    @ToString.Exclude
    private Appointment appointment;
}
