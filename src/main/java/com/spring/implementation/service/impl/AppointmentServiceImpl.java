package com.spring.implementation.service.impl;

import com.spring.implementation.dto.AppointmentRequestDTO;
import com.spring.implementation.dto.projection.AppointmentView;
import com.spring.implementation.entity.Appointment;
import com.spring.implementation.entity.Doctor;
import com.spring.implementation.entity.Patient;
import com.spring.implementation.repository.AppointmentRepository;
import com.spring.implementation.repository.DoctorRepository;
import com.spring.implementation.repository.PatientRepository;
import com.spring.implementation.service.AppointmentService;
import com.spring.implementation.service.DoctorService;
import com.spring.implementation.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;


    @Override
    public List<AppointmentView> findAllAppointment() {
        return appointmentRepository.findAllAppointment();
    }

    @Override
    public List<AppointmentView> findAppointmentForPatientByPatientId(Long id) {
       return appointmentRepository.findAppointmentForPatientByPatientId(id);
    }

    @Override
    public List<AppointmentView> findAppointmentForDoctorByDoctorId(Long id) {
        return appointmentRepository.findAppointmentForDoctorById(id);
    }

    @Override
    public void createNewAppointment(AppointmentRequestDTO appointmentRequestDTO) {
        Appointment appointment = Appointment.builder()
                .appointmentDate(appointmentRequestDTO.getAppointmentDate())
                .appointmentTime(appointmentRequestDTO.getAppointmentTime())
                .reasonForVisit(appointmentRequestDTO.getReasonForVisit())
                .status(appointmentRequestDTO.getStatus())
                .patient(Patient.toEntity(patientService.getPatientById(appointmentRequestDTO.getPatientId())))
                .doctor(Doctor.toEntity(doctorService.findDoctorById(appointmentRequestDTO.getDoctorId())))
                .build();

        appointmentRepository.save(appointment);

        }

}
