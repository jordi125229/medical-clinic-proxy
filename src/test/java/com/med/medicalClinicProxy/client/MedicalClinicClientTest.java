package com.med.medicalClinicProxy.client;

import com.med.medicalClinicProxy.exception.MedicalClinicException;
import feign.RetryableException;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.wiremock.spring.EnableWireMock;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableWireMock
@AutoConfigureMockMvc
public class MedicalClinicClientTest {

    @Autowired
    private MedicalClinicClient medicalClinicClient;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getVisits_DataCorrect_VisitsReturned() throws Exception {
        // given
        stubFor(get(urlPathEqualTo("/visits"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("/medical_client_response.json")));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/visits/patient")
                        .param("page", "0")
                        .param("size", "1")
                        .param("email", "patient email")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(1))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.content[0].doctorEmail").value("doctor email"))
                .andExpect(jsonPath("$.content[0].patientEmail").value("patient email"));

        verify(1, getRequestedFor(urlPathEqualTo("/visits"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1")));
    }

    @Test
    void getVisitsForDoctor_DataCorrect_VisitsReturned() throws Exception {
        // given
        stubFor(get(urlPathEqualTo("/visits"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("medical_client_response_available_visits.json")));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/visits/doctor")
                        .param("page", "0")
                        .param("size", "1")
                        .param("doctorEmail", "doctor email")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(1))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.content[0].doctorEmail").value("doctor email"))
                .andExpect(jsonPath("$.content[0].patientEmail").doesNotExist());

        verify(1, getRequestedFor(urlPathEqualTo("/visits"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1")));
    }

    @Test
    void getVisitsForDayByDoctorSpecialization_DataCorrect_VisitsReturned() throws Exception {
        // given
        int page = 0;
        int size = 1;
        LocalDate day = LocalDate.of(2025, 12, 4);
        String doctorSpecialization = "cardiologist";

        stubFor(get(urlPathEqualTo("/by-doctor-specialization"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1"))
                .withQueryParam("day", equalTo(("2025-12-04")))
                .withQueryParam("doctorSpecialization", equalTo("cardiologist"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("medical_client_response.json")));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/visits/doctor/day")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("day", day.toString())
                        .param("doctorSpecialization", doctorSpecialization)
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(page))
                .andExpect(jsonPath("$.pageSize").value(size))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.content[0].doctorEmail").value("doctor email"))
                .andExpect(jsonPath("$.content[0].patientEmail").value("patient email"));

        verify(1, getRequestedFor(urlPathEqualTo("/by-doctor-specialization"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("1"))
                .withQueryParam("day", equalTo("2025-12-04"))
                .withQueryParam("doctorSpecialization", equalTo("cardiologist")));
    }

    @Test
    void assignVisitToPatient_DataCorrect_PatientAssigned() throws Exception {
        // given
        stubFor(patch(urlPathEqualTo("/visits/email/id"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("visitDto_response.json")));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.patch("/visits/email/id")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorEmail").value("doctor email"))
                .andExpect(jsonPath("$.patientEmail").value("patient email"));

        verify(1, patchRequestedFor(urlPathEqualTo("/visits/email/id")));
    }

    @Test
    void feignRetry_WhenMedicalClinicReturns503_Retry() {
        // given
        stubFor(get(urlPathEqualTo("/visits")).willReturn(aResponse()
                        .withStatus(503)));

        // when
        assertThrows(MedicalClinicException.class, () -> medicalClinicClient.getVisits(0, 1));

        // then
        verify(5, getRequestedFor(urlPathEqualTo("/visits")));
    }

    @Test
    void getVisits_WhenMedicalClinicReturns503_RetryAndFallback() {
        // given
        stubFor(get(urlPathEqualTo("/visits")).willReturn(aResponse()
                        .withStatus(503)));

        // when
        MedicalClinicException exception = assertThrows(MedicalClinicException.class, () -> medicalClinicClient.getVisits(0, 1));

        // then
        assertEquals("Medical clinic app is not available now.", exception.getMessage());

        verify(5, getRequestedFor(urlPathEqualTo("/visits"))
                        .withQueryParam("page", equalTo("0"))
                        .withQueryParam("size", equalTo("1")));
    }
}
