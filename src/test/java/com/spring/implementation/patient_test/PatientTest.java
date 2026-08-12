package com.spring.implementation.patient_test;

import com.spring.implementation.entity.PatientEntity;
import com.spring.implementation.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PatientTest {

    @Autowired
    private  PatientRepository patientRepository;

    @Test
    public void getAllPatient(){
        List<PatientEntity> patients = patientRepository.findAll();
        patients.forEach(System.out::println);
    }


    @Test
    @Commit
    public void addNewPatient(){
        PatientEntity patient2 = new PatientEntity();
        patient2.setFullName("Alice Smith");
        patient2.setAge(26);
        patient2.setGender("Female");
        patient2.setPhoneNumber("9123456780");
        patient2.setEmail("alice.smith@example.com");
        patientRepository.save(patient2);
    }

    @Test
    public void findById(){
        Optional<PatientEntity> patient = patientRepository.findById(2L);
        System.out.println(patient);
    }
}
