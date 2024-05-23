package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.BookDTORequest;
import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.exceptionHandler.BookNotFoundException;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    private static final String ADMIN_KEY_HEADER = "X-ADMIN-KEY";
    private static final String ADMIN_KEY_VALUE = "ADMIN";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        // Given
        Book book1 = new Book(1234L, "Title1", "Author", 2000, "123-1234567890", false);
        Book book2 = new Book(12345L, "Title2", "Author", 2001, "123-0987654321", false);

        // When & Then
        when(bookService.getAllBooks()).thenReturn(List.of(book1, book2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetBookById() throws Exception {
        // Given
        Book book = new Book(1234L, "Title1", "Author", 2000, "123-1234567890", false);

        // When & Then
        when(bookService.getBookById(1234L)).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", book.getId())
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title1"));
    }

    @Test
    public void testAddBook() throws Exception {
        // Given
        BookDTORequest bookDTORequest = new BookDTORequest("Title1", "Author", 2000, "123-1234567890", false);
        Book book = new Book(1L, "Title1", "Author", 2000, "123-1234567890", false);

        // When & Then
        when(bookService.addBook(any())).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTORequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Title1"));
    }

    @Test
    public void testAddBookInvalidTitle() throws Exception {
        // Given
        BookDTORequest bookDTORequest = new BookDTORequest("", "Author", 2000, "123-1234567890", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTORequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddBookInvalidAuthor() throws Exception {
        // Given
        BookDTORequest bookDTORequest = new BookDTORequest("Title1", "Author1", 2000, "123-1234567890", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTORequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddBookInvalidPublicationYear() throws Exception {
        // Given
        BookDTORequest bookDTORequest = new BookDTORequest("Title1", "Author", 2025, "123-1234567890", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTORequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddBookInvalidISBN() throws Exception {
        // Given
        BookDTORequest bookDTORequest = new BookDTORequest("Title1", "Author", 2000, "12312345670", false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTORequest)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testUpdateBook() throws Exception {
        // Given
        Book bookDetails = new Book("UpdatedTitle", "UpdatedAuthor", 2001, "123-1234567890", false);
        Book book = new Book(1234L, "UpdatedTitle", "UpdatedAuthor", 2001, "123-1234567890", false);
        BookDTORequest updatedBookDTORequest = new BookDTORequest("UpdatedTitle", "UpdatedAuthor", 2001, "123-1234567890", false);

        // When & Then
        when(bookService.updateBook(1234L, bookDetails)).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", 1234L)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedBookDTORequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("UpdatedTitle"));
    }

    @Test
    public void testUpdateBookInvalidBookId() throws Exception {
        // Given
        Book bookDetails = new Book("UpdatedTitle", "UpdatedAuthor", 2001, "123-1234567890", false);
        Book book = new Book(1234L, "UpdatedTitle", "UpdatedAuthor", 2001, "123-1234567890", false);

        // When & Then
        doThrow(new BookNotFoundException("")).when(bookService).updateBook(-1L, bookDetails);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", -1)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateBookInvalidAuthor() throws Exception {
        // Given
        Book existingBook = new Book(100L, "Title", "Author", 2000, "123-1234567890", false);
        BookDTORequest updatedBook = new BookDTORequest("Title", "Author123", 2000, "123-1234567890", false);

        // When & Then
        when(bookService.updateBook(100L, existingBook)).thenThrow(new RuntimeException(""));
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
        BookDTORequest updatedBook = new BookDTORequest("Title", "Author123", 2000, "123-1234567890", false);

        // When & Then
        when(bookService.updateBook(100L, existingBook)).thenThrow(new RuntimeException(""));
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
        BookDTORequest updatedBook = new BookDTORequest("Title", "Author123", 2000, "1234567890", false);

        // When & Then
        when(bookService.updateBook(100L, existingBook)).thenThrow(new RuntimeException(""));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", existingBook.getId())
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedBook)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Given
        Book book = new Book(1234L, "Title1", "Author", 2000, "123-1234567890", false);

        // When & Then
        doNothing()
                .when(bookService).deleteBook(1234L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", book.getId())
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteBookNotExist() throws Exception {
        // When & Then
        doThrow(new BookNotFoundException(""))
                .when(bookService).deleteBook(1234L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", 1234L)
                        .header(ADMIN_KEY_HEADER, ADMIN_KEY_VALUE))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
