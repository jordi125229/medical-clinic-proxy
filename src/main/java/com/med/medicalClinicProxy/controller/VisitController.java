package com.med.medicalClinicProxy.controller;
import com.med.medicalClinicProxy.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.PageableDto;
import model.VisitDto;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
@Slf4j
public class VisitController {
    private final VisitService visitService;

    @GetMapping("/patient")
    public PageableDto<VisitDto> getVisitsForPatient(@RequestParam int page, @RequestParam int size, @RequestParam String email) {
        log.info("Getting patient's list");
        return visitService.getVisitsForPatient(page, size, email);
    }

    @GetMapping("/doctor")
    public PageableDto<VisitDto> getVisitsForDoctor(@RequestParam int page, @RequestParam int size,
                                                    @RequestParam String doctorEmail) {
        log.info("Getting visit's list for doctor");
        return visitService.getVisitsForDoctor(page, size, doctorEmail);
    }

    @GetMapping("/doctor/day")
    public PageableDto<VisitDto> getVisitsForDayByDoctorSpecialization(@RequestParam int page, @RequestParam int size, @RequestParam LocalDate day, @RequestParam String doctorSpecialization) {
        log.info("Getting visit for chosen day, by doctor's specialization");
       return visitService.getVisitsForDayByDoctorSpecialization(page, size, day, doctorSpecialization);
    }

    @PatchMapping("/{email}/{id}")
    public VisitDto assignVisitToPatient(@PathVariable String email, @PathVariable String id) {
        log.info("Assigning patient to scheduled visit");
        return visitService.assignPatientToVisit(email, id);
    }
}
