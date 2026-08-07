package com.spring.implementation.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String fullName;
    private Integer age;
    private String gender;
    private String phoneNumber;
    private String email;
//
//    @OneToMany(mappedBy = "patient")
//    @ToString.Exclude
//    private Set<Appointment> appointments = new HashSet<>();


}
