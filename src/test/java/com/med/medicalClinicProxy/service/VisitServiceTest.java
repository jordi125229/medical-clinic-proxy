package com.med.medicalClinicProxy.service;
import com.med.medicalClinicProxy.client.MedicalClinicClient;
import model.PageableDto;
import model.VisitDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VisitServiceTest {

    private MedicalClinicClient medicalClinicClient;
    private VisitService visitService;

    @BeforeEach
    void setUp() {
        this.medicalClinicClient = Mockito.mock(MedicalClinicClient.class);
        this.visitService = new VisitService(medicalClinicClient);
    }

    @Test
    void getVisitsForPatient_DataCorrect_VisitsForPatientReturned() {
        // given
        int pageNumber = 0;
        int pageSize = 1;
        VisitDto visit = VisitDto.builder()
                .doctorEmail("doctor email")
                .patientEmail("patient email")
                .visitStart(LocalDateTime.of(2025, 12, 4, 10, 0))
                .visitEnd(LocalDateTime.of(2025, 12, 4, 10, 30))
                .build();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<VisitDto> visits = List.of(visit);
        Page<VisitDto> pageVisit = new PageImpl<>(visits, pageRequest, visits.size());
        PageableDto<VisitDto> pageableDto = PageableDto.create(visits, pageVisit);
        when(medicalClinicClient.getVisits(pageNumber, pageSize)).thenReturn(pageableDto);

        // when
        PageableDto<VisitDto> pageableVisitDtoForPatient = visitService.getVisitsForPatient(pageNumber, pageSize, "patient email");

        // then
        assertAll(
                () -> assertEquals(1, pageableVisitDtoForPatient.getTotalPages()),
                () -> assertEquals("patient email", pageableVisitDtoForPatient.getContent().getFirst().getPatientEmail()),
                () -> assertEquals(visits, pageableVisitDtoForPatient.getContent())
        );
    }

    @Test
    void getVisitsForDoctor_DataCorrect_VisitsForDoctorReturned() {
        // given
        int pageNumber = 0;
        int pageSize = 1;
        VisitDto visit = VisitDto.builder()
                .doctorEmail("doctor email")
                .patientEmail("patient email")
                .visitStart(LocalDateTime.of(2025, 12, 4, 10, 0))
                .visitEnd(LocalDateTime.of(2025, 12, 4, 10, 30))
                .build();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<VisitDto> visits = List.of(visit);
        Page<VisitDto> pageVisit = new PageImpl<>(visits, pageRequest, visits.size());
        PageableDto<VisitDto> pageableDto = PageableDto.create(visits, pageVisit);
        when(medicalClinicClient.getVisits(pageNumber, pageSize)).thenReturn(pageableDto);

        // when
        PageableDto<VisitDto> pageableVisitDtoForDoctor = visitService.getVisitsForDoctor(pageNumber, pageSize, "doctor email");

        // then
        assertAll(
                () -> assertEquals(1, pageableVisitDtoForDoctor.getTotalPages()),
                () -> assertEquals("doctor email", pageableVisitDtoForDoctor.getContent().getFirst().getDoctorEmail()),
                () -> assertEquals(visits, pageableVisitDtoForDoctor.getContent()));
    }

    @Test
    void getVisitsForDayByDoctorSpecialization_DataCorrect_VisitsForDayByDoctorSpecializationReturned() {
        // given
        int pageNumber = 0;
        int pageSize = 1;
        VisitDto visit = VisitDto.builder()
                .doctorEmail("doctor email")
                .patientEmail("patient email")
                .visitStart(LocalDateTime.of(2025, 12, 4, 10, 0))
                .visitEnd(LocalDateTime.of(2025, 12, 4, 10, 30))
                .build();

        LocalDate day = visit.getVisitStart().toLocalDate();
        String doctorSpecialization = "cardiologist";

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<VisitDto> visits = List.of(visit);
        Page<VisitDto> pageVisit = new PageImpl<>(visits, pageRequest, visits.size());
        PageableDto<VisitDto> pageableDto = PageableDto.create(visits, pageVisit);
        when(medicalClinicClient.getVisitsForDayByDoctorSpecialization(pageNumber, pageSize, day, doctorSpecialization)).thenReturn(pageableDto);

        // when
        PageableDto<VisitDto> pageableVisitDtoForDayByDoctorSpecialization = visitService.getVisitsForDayByDoctorSpecialization(pageNumber, pageSize, day, doctorSpecialization);

        // then
        assertAll(
                () -> assertEquals(1, pageableVisitDtoForDayByDoctorSpecialization.getTotalPages()),
                () -> assertEquals("patient email", pageableVisitDtoForDayByDoctorSpecialization.getContent().getFirst().getPatientEmail()),
                () -> assertEquals("doctor email", pageableVisitDtoForDayByDoctorSpecialization.getContent().getFirst().getDoctorEmail()),
                () -> assertEquals(visits, pageableVisitDtoForDayByDoctorSpecialization.getContent()));
    }

    @Test
    void assignPatientToVisit_DataCorrect_PatientAssigned(){
        // given
        VisitDto visit = VisitDto.builder()
                .doctorEmail("doctor email")
                .patientEmail("patient email")
                .visitStart(LocalDateTime.of(2025, 12, 4, 10, 0))
                .visitEnd(LocalDateTime.of(2025, 12, 4, 10, 30))
                .build();

        String visitId = "1";

        when(medicalClinicClient.assignPatientToVisit("patient email", visitId)).thenReturn(visit);

        // when
        VisitDto visitAssignedToPatient = visitService.assignPatientToVisit("patient email", visitId);

        // then
        assertAll(
                () -> assertEquals(LocalDateTime.of(2025, 12, 4, 10, 0), visitAssignedToPatient.getVisitStart()),
                () -> assertEquals(LocalDateTime.of(2025, 12, 4, 10, 30), visitAssignedToPatient.getVisitEnd()),
                () -> assertEquals("doctor email", visitAssignedToPatient.getDoctorEmail()),
                () -> assertEquals("patient email", visitAssignedToPatient.getPatientEmail()));
    }
}
