package com.med.medicalClinicProxy.service;

import com.med.medicalClinicProxy.client.MedicalClinicClient;
import lombok.RequiredArgsConstructor;
import model.PageableDto;
import model.VisitDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public PageableDto<VisitDto> getVisitsForDayByDoctorSpecialization(int page, int size, LocalDate day, String doctorSpecialization) {
        return medicalClinicClient.getVisitsForDayByDoctorSpecialization(page, size, day, doctorSpecialization);
    }

    public VisitDto assignPatientToVisit(String patientEmail, String visitId) {
        return medicalClinicClient.assignPatientToVisit(patientEmail, visitId);
    }

//    public String cancelVisit(String doctorEmail, String visitId) {
//
//    }
}
