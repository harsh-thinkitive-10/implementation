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
import com.spring.implementation.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @Autowired
    private PatientService patientService;

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
        appointmentRepository.save(appointment);
    }

    @Test
    public void getAppointmentByPatientId(){
        Long id = 2L;
        List<AppointmentView> appointmentViews = appointmentRepository.findAppointmentForPatientByPatientId(id);
        List<AppointmentResponseDTO> appointmentResponseDTOS = new ArrayList<>();
        for (AppointmentView appointmentView : appointmentViews){
            appointmentResponseDTOS.add(AppointmentResponseDTO.convertToDTO(appointmentView));
        }
        appointmentResponseDTOS.forEach(System.out::println);
    }

}
