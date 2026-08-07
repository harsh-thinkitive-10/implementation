package com.spring.implementation.patient_test;

import com.spring.implementation.entity.Patient;
import com.spring.implementation.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
        List<Patient> patients = patientRepository.findAll();
        patients.forEach(System.out::println);
    }


    @Test
    @Commit
    public void addNewPatient(){
        Patient patient2 = new Patient();
        patient2.setFullName("Alice Smith");
        patient2.setAge(26);
        patient2.setGender("Female");
        patient2.setPhoneNumber("9123456780");
        patient2.setEmail("alice.smith@example.com");
        patientRepository.save(patient2);
    }

    @Test
    public void findById(){
        Optional<Patient> patient = patientRepository.findById(2L);
        System.out.println(patient);
    }
}
