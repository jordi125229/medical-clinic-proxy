package com.med.medicalClinicProxy.client;

import com.med.medicalClinicProxy.feignFallBack.FallBackMedicalClinicProxy;
import model.PageableDto;
import model.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@FeignClient(name = "medicalClinicPlaceHolder", configuration = MedicalClientConfig.class, fallbackFactory = FallBackMedicalClinicProxy.class)
public interface MedicalClinicClient {

    @GetMapping("/visits")
    PageableDto<VisitDto> getVisits(@RequestParam int page, @RequestParam int size);

    @GetMapping("/visits/patient")
    PageableDto<VisitDto> getVisitsForPatient(@RequestParam int page, @RequestParam int size, @RequestParam String email);

    @GetMapping("/visits/doctor")
    PageableDto<VisitDto> getVisitsForDoctor(@RequestParam int page, @RequestParam int size, @RequestParam String email);

    @GetMapping("/visits/doctor/available")
    PageableDto<VisitDto> getAvailableVisitsForDoctor(@RequestParam int page, @RequestParam int size, @RequestParam String email);

    @GetMapping("/visits/by-doctor-specialization")
    PageableDto<VisitDto> getVisitsByDoctorSpecialization(@RequestParam int page, @RequestParam int size, @RequestParam String doctorSpecialization);

    @GetMapping("/visits/by-doctor-specialization/day")
    PageableDto<VisitDto> getVisitsForDayByDoctorSpecialization(@RequestParam int page, @RequestParam int size, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate day, @RequestParam String doctorSpecialization);

    @GetMapping("/visits/by-period")
    PageableDto<VisitDto> getAvailableVisitsByPeriod(@RequestParam int page, @RequestParam int size,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                     @RequestParam(required = false) String specialization);

    @PatchMapping("/visits/{email}/{id}")
    VisitDto assignPatientToVisit(@PathVariable("email") String email, @PathVariable("id") String visitId);

    @DeleteMapping("/visits/{id}")
    void deleteVisit(@PathVariable("id") String visitId);
}

