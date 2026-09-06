package com.med.medicalClinicProxy.feignFallBack;

import com.med.medicalClinicProxy.client.MedicalClinicClient;
import com.med.medicalClinicProxy.exception.MedicalClinicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.PageableDto;
import model.VisitDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class FallBackMedicalClinicProxy implements FallbackFactory<MedicalClinicClient> {

    @Override
    public MedicalClinicClient create(Throwable cause) {
        log.error("Medical clinic app request has failed");
        return new MedicalClinicClient(){

            @Override
            public PageableDto<VisitDto> getVisits(int page, int size) {
                throw new MedicalClinicException("Medical clinic app is not available now.");
            }

            @Override
            public PageableDto<VisitDto> getVisitsForPatient(int page, int size, String email) {
                throw new MedicalClinicException("Medical clinic app is not available now.");
            }

            @Override
            public PageableDto<VisitDto> getVisitsForDoctor(int page, int size, String email) {
                throw new MedicalClinicException("Medical clinic app is not available now.");
            }

            @Override
            public PageableDto<VisitDto> getAvailableVisitsForDoctor(int page, int size, String email) {
                throw new MedicalClinicException("Medical clinic app is not available now.");
            }

            @Override
            public VisitDto assignPatientToVisit(String email, String visitId) {
                throw new MedicalClinicException("Medical clinic app is not available now.");
            }

            @Override
            public PageableDto<VisitDto> getVisitsForDayByDoctorSpecialization(int page, int size, LocalDate day, String doctorSpecialization) {
                throw new MedicalClinicException("Medical clinic app is not available now.");
            }
        };
    }
}
