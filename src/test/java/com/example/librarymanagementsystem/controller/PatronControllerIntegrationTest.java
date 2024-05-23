package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.PatronDTORequest;
import com.example.librarymanagementsystem.entity.Patron;
import com.example.librarymanagementsystem.exceptionHandler.PatronNotFoundException;
import com.example.librarymanagementsystem.service.PatronService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import static com.example.librarymanagementsystem.controller.BookControllerIntegrationTest.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class PatronControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    private static final String ADMIN_KEY_HEADER = "X-ADMIN-KEY";
    private static final String ADMIN_KEY_VALUE = "ADMIN";

    @Test
    void getAllPatronsSuccess() throws Exception {
        // Given
        Patron patron1 = new Patron(1L, "John Doe", "1234567890", "john@example.com");
        Patron patron2 = new Patron(2L, "Jane Smith", "9876543210", "jane@example.com");

        // When & Then
        when(patronService.getAllPatrons()).thenReturn(List.of(patron1, patron2));
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
        // Given
        Patron patron = new Patron(1L, "John Doe", "1234567890", "john@example.com");

        // When & Then
        when(patronService.getPatronById(1L)).thenReturn(patron);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }

    @Test
    void addPatronSuccess() throws Exception {
        // Given
        PatronDTORequest patronDTORequest = new PatronDTORequest("John Doe", "1234567890", "john@example.com");
        Patron patron = new Patron(1L, patronDTORequest.getName(), patronDTORequest.getPhoneNumber(), patronDTORequest.getEmailAddress());

        // When & Then
        when(patronService.addPatron(any())).thenReturn(patron);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patronDTORequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }

    @Test
    void updatePatronSuccess() throws Exception {
        // Given
        PatronDTORequest patronDTORequest = new PatronDTORequest("Jane Smith", "9876543210", "jane@example.com");
        Patron patronDetails = new Patron(patronDTORequest.getName(), patronDTORequest.getPhoneNumber(), patronDTORequest.getEmailAddress());
        Patron patron = new Patron(1L, patronDTORequest.getName(), patronDTORequest.getPhoneNumber(), patronDTORequest.getEmailAddress());

        // When & Then
        when(patronService.updatePatron(1L, patronDetails)).thenReturn(patron);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patronDTORequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jane Smith"));
    }

    @Test
    void deletePatronSuccess() throws Exception {
        // When & Then
        doNothing()
                .when(patronService).deletePatron(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }



    @Test
    void addPatronInvalidPatronDetails() throws Exception {
        // Given
        PatronDTORequest invalidPatronDTORequest = new PatronDTORequest("", "1234567890", "john@example.com");

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidPatronDTORequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updatePatronInvalidPatronDetails() throws Exception {
        // Given
        PatronDTORequest invalidPatronDTORequest = new PatronDTORequest("John Doe", "", "john@example.com");

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidPatronDTORequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deletePatronNotFound() throws Exception {
        // When & Then
        doThrow(new PatronNotFoundException(""))
                .when(patronService).deletePatron(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/1")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
