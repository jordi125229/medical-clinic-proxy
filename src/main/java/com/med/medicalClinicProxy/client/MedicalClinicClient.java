package com.med.medicalClinicProxy.client;

import com.med.medicalClinicProxy.feignFallBack.FallBackMedicalClinicProxy;
import model.PageableDto;
import model.VisitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "medicalClinicPlaceHolder", configuration = MedicalClientConfig.class, fallbackFactory = FallBackMedicalClinicProxy.class)
public interface MedicalClinicClient {

    @GetMapping("/visits")
    PageableDto<VisitDto> getVisits(@RequestParam int page, @RequestParam int size);

    @PatchMapping("/visits/{email}/{id}")
    VisitDto assignPatientToVisit(@PathVariable("email") String email, @PathVariable("id") String visitId);

    @GetMapping("/by-doctor-specialization")
    PageableDto<VisitDto> getVisitsForDayByDoctorSpecialization(@RequestParam int page, @RequestParam int size, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate day, @RequestParam String doctorSpecialization);
}

