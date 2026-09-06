package com.med.medicalClinicProxy.controller;

import com.med.medicalClinicProxy.service.VisitService;
import model.PageableDto;
import model.VisitDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VisitService visitService;

    @Test
    void getVisitsForPatient_DataCorrect_VisitReturned() throws Exception {
        // given
        int pageNumber = 0;
        int pageSize = 1;
        VisitDto visit = VisitDto.builder()
                .doctorEmail("doctor email")
                .patientEmail("patient email")
                .build();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<VisitDto> visits = List.of(visit);
        Page<VisitDto> pageVisit = new PageImpl<>(visits, pageRequest, visits.size());
        PageableDto<VisitDto> pageableVisitDto = PageableDto.create(visits, pageVisit);

        when(visitService.getVisitsForPatient(anyInt(), anyInt(), anyString())).thenReturn(pageableVisitDto);

        // when & then
        mockMvc.perform(get("/visits/patient")
                        .param("page", String.valueOf(pageNumber))
                        .param("size", String.valueOf(pageSize))
                        .param("email", "patient email")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(pageNumber))
                .andExpect(jsonPath("$.pageSize").value(pageSize))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.content[0].doctorEmail").value("doctor email"))
                .andExpect(jsonPath("$.content[0].patientEmail").value("patient email"));

    }

    @Test
    void getVisitsForDoctor_DataCorrect_VisitReturned() throws Exception {
        // given
        int pageNumber = 0;
        int pageSize = 1;
        VisitDto visit = VisitDto.builder()
                .doctorEmail("doctor email")
                .patientEmail("patient email")
                .build();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<VisitDto> visits = List.of(visit);
        Page<VisitDto> pageVisit = new PageImpl<>(visits, pageRequest, visits.size());
        PageableDto<VisitDto> pageableVisitDto = PageableDto.create(visits, pageVisit);

        when(visitService.getAvailableVisitsForDoctor(anyInt(), anyInt(), anyString())).thenReturn(pageableVisitDto);

        // when & then
        mockMvc.perform(get("/visits/doctor")
                        .param("page", String.valueOf(pageNumber))
                        .param("size", String.valueOf(pageSize))
                        .param("doctorEmail", "doctor email")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(pageNumber))
                .andExpect(jsonPath("$.pageSize").value(pageSize))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.content[0].doctorEmail").value("doctor email"))
                .andExpect(jsonPath("$.content[0].patientEmail").value("patient email"));
    }

    @Test
    void getVisitsForDayByDoctorSpecialization_DataCorrect_VisitReturned() throws Exception {
        // given
        int pageNumber = 0;
        int pageSize = 1;
        String doctorSpecialization = "cardiologist";
        LocalDate day = LocalDate.of(2025, 12, 4);
        VisitDto visit = VisitDto.builder()
                .doctorEmail("doctor email")
                .patientEmail("patient email")
                .build();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<VisitDto> visits = List.of(visit);
        Page<VisitDto> pageVisit = new PageImpl<>(visits, pageRequest, visits.size());
        PageableDto<VisitDto> pageableVisitDto = PageableDto.create(visits, pageVisit);

        when(visitService.getVisitsForDayByDoctorSpecialization(anyInt(), anyInt(), eq(day), anyString())).thenReturn(pageableVisitDto);

        // when & then
        mockMvc.perform(get("/visits/doctor/day")
                        .param("page", String.valueOf(pageNumber))
                        .param("size", String.valueOf(pageSize))
                        .param("day", day.toString())
                        .param("doctorSpecialization", doctorSpecialization)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(pageNumber))
                .andExpect(jsonPath("$.pageSize").value(pageSize))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.content[0].doctorEmail").value("doctor email"))
                .andExpect(jsonPath("$.content[0].patientEmail").value("patient email"));
    }

    @Test
    void assignPatientToVisit_DataCorrect_VisitReturned() throws Exception {
        // given
        VisitDto visit = VisitDto.builder()
                .doctorEmail("doctor email")
                .patientEmail("patient email")
                .build();

        when(visitService.assignPatientToVisit(anyString(), anyString())).thenReturn(visit);

        // when & then
        mockMvc.perform(patch("/visits/patient email/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorEmail").value("doctor email"))
                .andExpect(jsonPath("$.patientEmail").value("patient email"));
    }
}
