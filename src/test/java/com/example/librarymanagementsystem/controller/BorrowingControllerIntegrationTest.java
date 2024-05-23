package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.exceptionHandler.BookAlreadyBorrowedException;
import com.example.librarymanagementsystem.exceptionHandler.BookNotFoundException;
import com.example.librarymanagementsystem.service.BorrowingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class BorrowingControllerIntegrationTest {

    private static final String ADMIN_KEY_HEADER = "X-ADMIN-KEY";
    private static final String ADMIN_KEY_VALUE = "ADMIN";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingService borrowingService;

    @Test
    public void testBorrowBookSuccess() throws Exception {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        // When & Then
        doNothing()
                .when(borrowingService).borrowBook(bookId, patronId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    public void testReturnBookSuccess() throws Exception {
        // Given
        Long bookId = 1L;
        Long patronId = 1L;

        // When & Then
        doNothing()
                .when(borrowingService).returnBook(bookId, patronId);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testBorrowBookInvalidBookId() throws Exception {
        // Given
        Long invalidBookId = -1L;
        Long patronId = 1L;

        // When & Then
        doThrow(new BookNotFoundException("Book not found with id: " + invalidBookId))
                .when(borrowingService).borrowBook(invalidBookId, patronId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/{bookId}/patron/{patronId}", invalidBookId, patronId)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testBorrowBookAlreadyBorrowed() throws Exception {
        // Given
        Long invalidBookId = 1L;
        Long patronId = 1L;

        // When & Then
        doThrow(new BookAlreadyBorrowedException("Book not found with id: " + invalidBookId))
                .when(borrowingService).borrowBook(invalidBookId, patronId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/{bookId}/patron/{patronId}", invalidBookId, patronId)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void testReturnBookInvalidPatronId() throws Exception {
        // Given
        Long bookId = 1L;
        Long invalidPatronId = -1L;

        // When & Then
        doThrow(new BookNotFoundException("Book not found with id: " + bookId))
                .when(borrowingService).returnBook(bookId, invalidPatronId);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/borrow/{bookId}/patron/{patronId}", bookId, invalidPatronId)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
