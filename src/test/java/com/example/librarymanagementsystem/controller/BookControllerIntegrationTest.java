package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.BookDTO;
import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    private static final String ADMIN_KEY_HEADER = "X-ADMIN-KEY";
    private static final String ADMIN_KEY_VALUE = "ADMIN";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book(1234L, "Title1", "Author", 2000, "123-1234567890", false);
        Book book2 = new Book(12345L, "Title2", "Author", 2001, "123-0987654321", false);
        bookRepository.saveAll(List.of(book1, book2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book(1234L, "Title1", "Author", 2000, "123-1234567890", false);
        book = bookRepository.save(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", book.getId())
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title1"));
    }

    @Test
    public void testAddBook() throws Exception {
        BookDTO bookDTO = new BookDTO("Title1", "Author", 2000, "123-1234567890", false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Title1"));
    }

    @Test
    public void testAddBookInvalidTitle() throws Exception {
        // Given
        BookDTO bookDTO = new BookDTO("", "Author", 2000, "123-1234567890", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddBookInvalidAuthor() throws Exception {
        // Given
        BookDTO bookDTO = new BookDTO("Title1", "Author1", 2000, "123-1234567890", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddBookInvalidPublicationYear() throws Exception {
        // Given
        BookDTO bookDTO = new BookDTO("Title1", "Author", 2025, "123-1234567890", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddBookInvalidISBN() throws Exception {
        // Given
        BookDTO bookDTO = new BookDTO("Title1", "Author", 2000, "12312345670", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetBookByIdInvalidId() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", 999)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book(1234L, "Title1", "Author", 2000, "123-1234567890", false);
        book = bookRepository.save(book);

        BookDTO updatedBookDTO = new BookDTO("UpdatedTitle", "UpdatedAuthor", 2001, "123-1234567890", false);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", book.getId())
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedBookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("UpdatedTitle"));
    }

    @Test
    public void testUpdateBookInvalidBookId() throws Exception {
        // Given
        Book book = new Book(100L, "Title", "Author", 2000, "123-1234567890"); // Assuming book with ID 100 does not exist

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", 100)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBookInvalidAuthor() throws Exception {
        // Given
        Book existingBook = new Book(100L, "Title", "Author", 2000, "123-1234567890", false);
        bookRepository.save(existingBook);

        BookDTO updatedBook = new BookDTO("Title", "Author123", 2000, "123-1234567890", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", existingBook.getId())
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedBook)))
                .andExpect(status().isBadRequest()); // Expecting 400 Bad Request due to invalid author
    }

    @Test
    public void testUpdateBookInvalidPublicationYear() throws Exception {
        // Given
        Book existingBook = new Book(100L, "Title", "Author", 2000, "123-1234567890", false);
        bookRepository.save(existingBook);

        BookDTO updatedBook = new BookDTO("Title", "Author123", 2000, "123-1234567890", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", existingBook.getId())
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedBook)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBookInvalidIsbn() throws Exception {
        // Given
        Book existingBook = new Book(100L, "Title", "Author", 2000, "123-1234567890", false);
        bookRepository.save(existingBook);

        BookDTO updatedBook = new BookDTO("Title", "Author123", 2000, "1234567890", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", existingBook.getId())
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedBook)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = new Book(1234L, "Title1", "Author", 2000, "123-1234567890", false);
        book = bookRepository.save(book);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", book.getId())
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteBookNotExist() throws Exception {
        Book book = new Book(1234L, "Title1", "Author", 2000, "123-1234567890", false);
        bookRepository.save(book);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", 12L)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE))
                .andExpect(status().isNotFound());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
