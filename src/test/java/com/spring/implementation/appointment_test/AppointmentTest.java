package com.spring.implementation.appointment_test;

import com.spring.implementation.dto.AppointmentResponseDTO;
import com.spring.implementation.dto.projection.AppointmentView;
import com.spring.implementation.entity.Appointment;
import com.spring.implementation.entity.Doctor;
import com.spring.implementation.entity.Patient;
import com.spring.implementation.repository.AppointmentRepository;
import com.spring.implementation.repository.DoctorRepository;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Slf4j
public class AppointmentTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    @Commit
    public void addNewAppointment(){
        Patient patient = patientRepository.findById(3L).orElseThrow(()->new RuntimeException("not found"));
        Doctor doctor = doctorRepository.findById(2L).orElseThrow(()->new RuntimeException("not found"));

        Appointment appointment = new Appointment();

        appointment.setAppointmentDate(LocalDate.of(2026, 8, 10));
        appointment.setAppointmentTime(LocalTime.of(10, 30));
        appointment.setStatus("SCHEDULED");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

//        patient.getAppointments().add(appointment);
//        doctor.getAppointments().add(appointment);

        appointmentRepository.save(appointment);
    }


    @Test
    @Commit
    public void getAllPatientAppointments(){
        long id = 3;
        List<Appointment> appointment = appointmentRepository.findAppointmentByPatientPatientId(id);
        log.info("Appointment size : {}", appointment.size());

        for (Appointment appointment1 : appointment) {
            log.info("Appointment for patient name : {}", appointment1.getPatient().getFullName());
            log.info("Appointment for doctor : {}", appointment1.getDoctor().getFullName());
            log.info("Appointment date : {}", appointment1.getAppointmentDate());
        }
    }

    @Test
    public void getAppointmentByPatientId(){
        Long id = 2L;
        List<AppointmentView> patient = appointmentRepository.findAppointmentForPatientByPatientId(id);
        //patient.stream().forEach(System.out::println);
        for (AppointmentView a : patient) {
            log.info("patient name: {}",a.getPatientName());
            log.info("doctor name: {}",a.getDoctorName());
            log.info("appointment date: {}",a.getAppointmentDate());
            log.info("patient name: {} doctor name: {} appointment date: {}",a.getPatientName(),a.getDoctorName(),a.getAppointmentDate());
        }

    }

}
