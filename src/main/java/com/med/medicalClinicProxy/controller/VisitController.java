package com.med.medicalClinicProxy.controller;
import com.med.medicalClinicProxy.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.PageableDto;
import model.VisitDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
@Slf4j
public class VisitController {

    private final VisitService visitService;

    @GetMapping("/patient")
    public PageableDto<VisitDto> getVisitsForPatient(@RequestParam int page, @RequestParam int size, @RequestParam String email) {
        log.info("Getting patient's visits");
        return visitService.getVisitsForPatient(page, size, email);
    }

    @GetMapping("/doctor")
    public PageableDto<VisitDto> getVisitsForDoctor(@RequestParam int page, @RequestParam int size, @RequestParam String doctorEmail) {
        log.info("Getting visits for doctor");
        return visitService.getVisitsForDoctor(page, size, doctorEmail);
    }

    @GetMapping("/doctor/available")
    public PageableDto<VisitDto> getAvailableVisitsForDoctor(@RequestParam int page, @RequestParam int size, @RequestParam String doctorEmail) {
        log.info("Getting available visits for doctor");
        return visitService.getAvailableVisitsForDoctor(page, size, doctorEmail);
    }

    @GetMapping("/doctor/specialization")
    public PageableDto<VisitDto> getVisitsByDoctorSpecialization(@RequestParam int page, @RequestParam int size, @RequestParam String doctorEmail) {
        log.info("Getting available visits for doctor by specialization");
        return visitService.getVisitsByDoctorSpecialization(page, size, doctorEmail);
    }

    @GetMapping("/doctor/day")
    public PageableDto<VisitDto> getVisitsForDayByDoctorSpecialization(@RequestParam int page, @RequestParam int size, @RequestParam LocalDate day, @RequestParam String doctorSpecialization) {
        log.info("Getting available visits for chosen day and specialization");
        return visitService.getVisitsForDayByDoctorSpecialization(page, size, day, doctorSpecialization);
    }

    @GetMapping("/by-period")
    public PageableDto<VisitDto> getAvailableVisitsByPeriod(@RequestParam int page, @RequestParam int size, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                            @RequestParam(required = false) String specialization) {
        return visitService.getAvailableVisitsByPeriod(page, size, start, end, specialization);
    }

    @PatchMapping("/{email}/{id}")
    public VisitDto assignVisitToPatient(@PathVariable String email, @PathVariable String id) {
        log.info("Assigning patient to scheduled visit");
        return visitService.assignPatientToVisit(email, id);
    }

    @DeleteMapping("/resignation")
    public String cancelVisit(@RequestParam String visitId) {
        log.info("Cancelling visit");
        return visitService.cancelVisit(visitId);
    }
}
