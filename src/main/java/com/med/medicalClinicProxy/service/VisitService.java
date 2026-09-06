package com.med.medicalClinicProxy.service;

import com.med.medicalClinicProxy.client.MedicalClinicClient;
import lombok.RequiredArgsConstructor;
import model.PageableDto;
import model.VisitDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final MedicalClinicClient medicalClinicClient;

    public PageableDto<VisitDto> getVisitsForPatient(int page, int size, String email) {
        return medicalClinicClient.getVisitsForPatient(page, size, email);
    }

    public PageableDto<VisitDto> getVisitsForDoctor(int page, int size, String doctorEmail) {
        return medicalClinicClient.getVisitsForDoctor(page, size, doctorEmail);
    }

    public PageableDto<VisitDto> getAvailableVisitsForDoctor(int page, int size, String doctorEmail) {
        return medicalClinicClient.getAvailableVisitsForDoctor(page, size, doctorEmail);
    }

    public PageableDto<VisitDto> getVisitsByDoctorSpecialization(int page, int size, String doctorSpecialization) {
        return medicalClinicClient.getVisitsByDoctorSpecialization(page, size, doctorSpecialization);
    }

    public PageableDto<VisitDto> getVisitsForDayByDoctorSpecialization(int page, int size, LocalDate day, String doctorSpecialization) {
        return medicalClinicClient.getVisitsForDayByDoctorSpecialization(page, size, day, doctorSpecialization);
    }

    public PageableDto<VisitDto> getAvailableVisitsByPeriod(int page, int size, LocalDateTime start, LocalDateTime end, String specialization) {
        return medicalClinicClient.getAvailableVisitsByPeriod(page, size, start, end, specialization);
    }

    public VisitDto assignPatientToVisit(String patientEmail, String visitId) {
        return medicalClinicClient.assignPatientToVisit(patientEmail, visitId);
    }

    public String cancelVisit(String visitId) {
        medicalClinicClient.deleteVisit(visitId);
        return "Visit deleted";
    }
}
