package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.PatronDTO;
import com.example.librarymanagementsystem.entity.Patron;
import com.example.librarymanagementsystem.repository.PatronRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class PatronControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatronRepository patronRepository;


    private static final String ADMIN_KEY_HEADER = "X-ADMIN-KEY";
    private static final String ADMIN_KEY_VALUE = "ADMIN";

    @Test
    void getAllPatronsSuccess() throws Exception {

        Patron patron1 = new Patron(1L, "John Doe", "1234567890", "john@example.com");
        Patron patron2 = new Patron(2L, "Jane Smith", "9876543210", "jane@example.com");
        patronRepository.saveAll(List.of(patron1, patron2));

        // Performing GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    void getPatronByIdSuccess() throws Exception {
        Patron patron = new Patron(1L, "John Doe", "1234567890", "john@example.com");
        patronRepository.save(patron);

        // Performing GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }

    @Test
    void addPatronSuccess() throws Exception {
        // Given
        PatronDTO patronDTO = new PatronDTO("John Doe", "1234567890", "john@example.com");
        Patron patron = new Patron(1L, patronDTO.getName(), patronDTO.getPhoneNumber(), patronDTO.getEmailAddress());
        patronRepository.save(patron);

        // Performing POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }

    @Test
    void updatePatronSuccess() throws Exception {
        // Given
        PatronDTO patronDTO = new PatronDTO("Jane Smith", "9876543210", "jane@example.com");
        Patron patron = new Patron(1L, patronDTO.getName(), patronDTO.getPhoneNumber(), patronDTO.getEmailAddress());
        patronRepository.save(patron);

        // Performing PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jane Smith"));
    }

    @Test
    void deletePatronSuccess() throws Exception {
        Patron patron = new Patron(1L, "John Doe", "1234567890", "john@example.com");
        patronRepository.save(patron);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void getPatronByIdPatronNotFound() throws Exception {

        // Performing GET request for non-existing patron
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addPatronInvalidPatronDetails() throws Exception {
        // Given an invalid patron DTO with empty name
        PatronDTO invalidPatronDTO = new PatronDTO("", "1234567890", "john@example.com");

        // Performing POST request with invalid patron DTO
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPatronDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updatePatronInvalidPatronDetails() throws Exception {
        // Given an invalid patron DTO with empty phone number
        PatronDTO invalidPatronDTO = new PatronDTO("John Doe", "", "john@example.com");

        // Performing PUT request with invalid patron DTO
        mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPatronDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deletePatronNotFound() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
