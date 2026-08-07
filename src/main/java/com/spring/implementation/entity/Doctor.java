package com.spring.implementation.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    private String fullName;
    private String specialization;
    private String phoneNumber;
    private String email;
    private Double consultationFee;


//    @OneToMany(mappedBy = "doctor")
//    @ToString.Exclude
//    private List<Appointment> appointments;
}
