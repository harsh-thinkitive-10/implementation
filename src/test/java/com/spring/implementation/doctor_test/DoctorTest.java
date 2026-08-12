package com.spring.implementation.doctor_test;

import com.spring.implementation.entity.DoctorEntity;
import com.spring.implementation.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class DoctorTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    @Commit
    public void addNewDoctor(){
        DoctorEntity doctor3 = new DoctorEntity();
        doctor3.setFullName("Dr. Sophia Wilson");
        doctor3.setSpecialization("Orthopedic");
        doctor3.setPhoneNumber("9988776655");
        doctor3.setEmail("sophia.wilson@hospital.com");
        doctor3.setConsultationFee(BigDecimal.valueOf(1000.0));
        doctorRepository.save(doctor3);
    }

    @Test
    public void getAllDoctor(){
        List<DoctorEntity> doctorEntities = doctorRepository.findAll();
        doctorEntities.forEach(System.out::println);
    }

}
