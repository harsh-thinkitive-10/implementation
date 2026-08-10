package com.spring.implementation.dto;
import com.spring.implementation.dto.projection.AppointmentView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reasonForVisit;
    private String status;
    private PatientDTO patient;
    private DoctorDTO doctor;

    public static AppointmentResponseDTO convertToDTO(
            AppointmentView view) {

        PatientDTO patient = new PatientDTO();

        patient.setFullName(view.getPatientFullName());
        patient.setAge(view.getPatientAge());
        patient.setGender(view.getPatientGender());
        patient.setPhoneNumber(view.getPatientPhoneNumber());
        patient.setEmail(view.getPatientEmail());

        DoctorDTO doctor = new DoctorDTO();

        doctor.setFullName(view.getDoctorFullName());
        doctor.setSpecialization(view.getDoctorSpecialization());
        doctor.setPhoneNumber(view.getDoctorPhoneNumber());
        doctor.setEmail(view.getDoctorEmail());
        doctor.setConsultationFee(view.getConsultationFee());

        AppointmentResponseDTO dto = new AppointmentResponseDTO();

        dto.setAppointmentDate(view.getAppointmentDate());
        dto.setAppointmentTime(view.getAppointmentTime());
        dto.setReasonForVisit(view.getReasonForVisit());
        dto.setStatus(view.getStatus());

        dto.setPatient(patient);
        dto.setDoctor(doctor);

        return dto;
    }
}