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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
        String patientEmail = "patient email";

        VisitDto visit = VisitDto.builder()
                .doctorEmail("doctor email")
                .patientEmail(patientEmail)
                .visitStart(LocalDateTime.of(2025, 12, 4, 10, 0))
                .visitEnd(LocalDateTime.of(2025, 12, 4, 10, 30))
                .build();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<VisitDto> visits = List.of(visit);
        Page<VisitDto> pageVisit = new PageImpl<>(visits, pageRequest, visits.size());

        PageableDto<VisitDto> pageableDto = PageableDto.create(visits, pageVisit);
        when(medicalClinicClient.getVisitsForPatient(pageNumber, pageSize, patientEmail)).thenReturn(pageableDto);

        // when
        PageableDto<VisitDto> result = visitService.getVisitsForPatient(pageNumber, pageSize, patientEmail);

        // then
        assertAll(
                () -> assertEquals(1, result.getTotalPages()),
                () -> assertEquals(patientEmail, result.getContent().getFirst().getPatientEmail()),
                () -> assertEquals(visits, result.getContent()));

        verify(medicalClinicClient).getVisitsForPatient(pageNumber, pageSize, patientEmail);
    }

    @Test
    void getVisitsForDoctor_DataCorrect_VisitsForDoctorReturned() {
        // given
        int pageNumber = 0;
        int pageSize = 1;
        String doctorEmail = "doctor email";

        VisitDto visit = VisitDto.builder()
                .doctorEmail(doctorEmail)
                .patientEmail(null)
                .visitStart(LocalDateTime.of(2025, 12, 4, 10, 0))
                .visitEnd(LocalDateTime.of(2025, 12, 4, 10, 30))
                .build();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<VisitDto> visits = List.of(visit);
        Page<VisitDto> pageVisit = new PageImpl<>(visits, pageRequest, visits.size());
        PageableDto<VisitDto> pageableDto = PageableDto.create(visits, pageVisit);

        when(medicalClinicClient.getVisitsForDoctor(pageNumber, pageSize, doctorEmail)).thenReturn(pageableDto);

        // when
        PageableDto<VisitDto> result =
                visitService.getVisitsForDoctor(pageNumber, pageSize, doctorEmail);

        // then
        assertAll(
                () -> assertEquals(1, result.getTotalPages()),
                () -> assertEquals(doctorEmail, result.getContent().getFirst().getDoctorEmail()),
                () -> assertEquals(visits, result.getContent())
        );

        verify(medicalClinicClient).getVisitsForDoctor(pageNumber, pageSize, doctorEmail);
    }

    @Test
    void getAvailableVisitsForDoctor_DataCorrect_AvailableVisitsReturned() {
        // given
        int pageNumber = 0;
        int pageSize = 1;
        String doctorEmail = "doctor email";

        VisitDto visit = VisitDto.builder()
                .doctorEmail(doctorEmail)
                .patientEmail(null)
                .visitStart(LocalDateTime.of(2025, 12, 4, 10, 0))
                .visitEnd(LocalDateTime.of(2025, 12, 4, 10, 30))
                .build();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<VisitDto> visits = List.of(visit);
        Page<VisitDto> pageVisit = new PageImpl<>(visits, pageRequest, visits.size());
        PageableDto<VisitDto> pageableDto = PageableDto.create(visits, pageVisit);

        when(medicalClinicClient.getAvailableVisitsForDoctor(pageNumber, pageSize, doctorEmail)).thenReturn(pageableDto);

        // when
        PageableDto<VisitDto> result = visitService.getAvailableVisitsForDoctor(pageNumber, pageSize, doctorEmail);

        // then
        assertAll(
                () -> assertEquals(1, result.getTotalPages()),
                () -> assertEquals(doctorEmail, result.getContent().getFirst().getDoctorEmail()),
                () -> assertNull(result.getContent().getFirst().getPatientEmail()),
                () -> assertEquals(visits, result.getContent())
        );

        verify(medicalClinicClient).getAvailableVisitsForDoctor(pageNumber, pageSize, doctorEmail);
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
    void assignPatientToVisit_DataCorrect_PatientAssigned() {
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
