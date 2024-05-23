package com.example.librarymanagementsystem.serviceImpl;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.exceptionHandler.BookISBNAlreadyExistsException;
import com.example.librarymanagementsystem.exceptionHandler.BookNotFoundException;
import com.example.librarymanagementsystem.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Title1", "Author1", 2021, "ISBN1"));
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    public void testGetBookById_Success() {
        Book book = new Book(1L, "Title1", "Author1", 2021, "ISBN1");
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
    }

    @Test
    public void testGetBookById_NotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
    public void testAddBook_Success() {
        Book book = new Book(1L, "Title1", "Author1", 2021, "ISBN1");
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.addBook(book);

        assertNotNull(result);
        assertEquals("Title1", result.getTitle());
    }

    @Test
    public void testAddBook_DuplicateISBN() {
        when(bookRepository.save(any(Book.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(BookISBNAlreadyExistsException.class, () -> bookService.addBook(new Book()));
    }

    @Test
    public void testUpdateBook_Success() {
        Book book = new Book(1L, "Title1", "Author1", 2021, "ISBN1");
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedDetails = new Book(1L, "UpdatedTitle", "UpdatedAuthor", 2022, "ISBN1");
        Book result = bookService.updateBook(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("UpdatedTitle", result.getTitle());
    }

    @Test
    public void testUpdateBook_NotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, new Book()));
    }

    @Test
    public void testDeleteBook_Success() {
        Book book = new Book(1L, "Title1", "Author1", 2021, "ISBN1");
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(any(Book.class));

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    public void testDeleteBook_NotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }
}
