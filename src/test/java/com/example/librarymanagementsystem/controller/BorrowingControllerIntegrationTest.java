package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.service.BorrowingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BorrowingControllerIntegrationTest {

    private static final String ADMIN_KEY_HEADER = "X-ADMIN-KEY";
    private static final String ADMIN_KEY_VALUE = "ADMIN";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BorrowingService borrowingService;

    // Good Scenarios

    @Test
    public void testBorrowBook_Success() throws Exception {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testReturnBook_Success() throws Exception {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Bad Scenarios

    @Test
    public void testBorrowBookInvalidBookId() throws Exception {
        // Given
        Long invalidBookId = -1L;
        Long patronId = 1L;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/{bookId}/patron/{patronId}", invalidBookId, patronId)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testReturnBookInvalidPatronId() throws Exception {
        // Given
        Long bookId = 1L;
        Long invalidPatronId = -1L;

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/borrow/{bookId}/patron/{patronId}", bookId, invalidPatronId)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
