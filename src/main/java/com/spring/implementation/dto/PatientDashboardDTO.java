package com.spring.implementation.dto;

import com.spring.implementation.dto.projection.PatientDashboardProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDashboardDTO {
    private PatientSummary patient;
    private AppointmentSummary appointments;
    private PrescriptionSummary prescriptions;
    private MedicalRecordSummary medicalRecords;
    private LabReportSummary labReports;
    private NextAppointment nextAppointment;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientSummary {
        private String fullName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppointmentSummary {
        private Long upcoming;
        private Long completed;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrescriptionSummary {
        private Long active;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalRecordSummary {
        private Long total;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LabReportSummary {
        private Long available;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NextAppointment {
        private String date;
        private String time;
        private String doctorName;
        private String specialization;
    }
    public static PatientDashboardDTO toDTO(
            PatientDashboardProjection projection
    ) {

        return PatientDashboardDTO.builder()

                .patient(
                        PatientSummary.builder()
                                .fullName(projection.getFullName())
                                .build()
                )

                .appointments(
                        AppointmentSummary.builder()
                                .upcoming(
                                        projection.getUpcomingAppointments()
                                )
                                .completed(
                                        projection.getCompletedAppointments()
                                )
                                .build()
                )

                .prescriptions(
                        PrescriptionSummary.builder()
                                .active(
                                        projection.getActivePrescriptions()
                                )
                                .build()
                )

                .medicalRecords(
                        MedicalRecordSummary.builder()
                                .total(
                                        projection.getTotalMedicalRecords()
                                )
                                .build()
                )

                .labReports(
                        LabReportSummary.builder()
                                .available(
                                        projection.getAvailableLabReports()
                                )
                                .build()
                )

                .nextAppointment(
                        NextAppointment.builder()
                                .date(
                                        projection.getNextAppointmentDate()
                                )
                                .time(
                                        projection.getNextAppointmentTime()
                                )
                                .doctorName(
                                        projection.getDoctorName()
                                )
                                .specialization(
                                        projection.getSpecialization()
                                )
                                .build()
                )

                .build();
    }
}
