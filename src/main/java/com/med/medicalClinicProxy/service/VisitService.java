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
        PageableDto<VisitDto> visits = getVisitDtoPage(page, size);
        List<VisitDto> visitListForPatient = visits.getContent().stream()
                .filter(visitDto -> visitDto.getPatientEmail().equalsIgnoreCase(email))
                .toList();
        visits.setContent(visitListForPatient);
        return visits;
    }

    public PageableDto<VisitDto> getAvailableVisitsForDoctor(int page, int size, String doctorEmail) {
        PageableDto<VisitDto> visits = getVisitDtoPage(page, size);
        List<model.VisitDto> visitListForDoctor = visits.getContent().stream()
                .filter(visit -> visit.getDoctorEmail().equalsIgnoreCase(doctorEmail))
                .filter(visit -> visit.getPatientEmail() == null)
                .toList();
        visits.setContent(visitListForDoctor);
        return visits;
    }

    public PageableDto<VisitDto> getVisitsForDayByDoctorSpecialization(int page, int size, LocalDate day, String doctorSpecialization){
        return medicalClinicClient.getVisitsForDayByDoctorSpecialization(page, size, day, doctorSpecialization);
    }

    public VisitDto assignPatientToVisit(String patientEmail, String visitId) {  // tu trzeba przetestowac
        return medicalClinicClient.assignPatientToVisit(patientEmail, visitId);
    }

    private PageableDto<VisitDto> getVisitDtoPage(int page, int size) {
        return medicalClinicClient.getVisits(page, size);
    }
}
