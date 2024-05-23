package com.example.librarymanagementsystem.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.BorrowingRecord;
import com.example.librarymanagementsystem.entity.Patron;
import com.example.librarymanagementsystem.exceptionHandler.BookAlreadyBorrowedException;
import com.example.librarymanagementsystem.exceptionHandler.BorrowingRecordNotFoundException;
import com.example.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.example.librarymanagementsystem.service.BookService;
import com.example.librarymanagementsystem.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class BorrowingServiceImplTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookService bookService;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private BorrowingServiceImpl borrowingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testBorrowBook_Success() {
        Book book = new Book(1L, "Title1", "Author1", 2000, "1234567890", false);
        Patron patron = new Patron(1L, "Patron1", "1234567890", "patron1@example.com");

        when(bookService.getBookById(1L)).thenReturn(book);
        when(patronService.getPatronById(1L)).thenReturn(patron);

        borrowingService.borrowBook(1L, 1L);

        assertTrue(book.isBorrowed());
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void testBorrowBook_AlreadyBorrowed() {
        Book book = new Book(1L, "Title1", "Author1", 2000, "1234567890", true);
        when(bookService.getBookById(1L)).thenReturn(book);

        assertThrows(BookAlreadyBorrowedException.class, () -> borrowingService.borrowBook(1L, 1L));
    }

    @Test
    void testReturnBook_Success() {
        Book book = new Book(1L, "Title1", "Author1", 2000, "1234567890", true);
        Patron patron = new Patron(1L, "Patron1", "1234567890", "patron1@example.com");
        BorrowingRecord borrowingRecord = new BorrowingRecord(book, patron);

        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L))
                .thenReturn(Optional.of(borrowingRecord));

        borrowingService.returnBook(1L, 1L);

        assertNotNull(borrowingRecord.getReturnDate());
        assertFalse(book.isBorrowed());
        verify(borrowingRecordRepository, times(1)).save(borrowingRecord);
    }

    @Test
    void testReturnBook_BorrowingRecordNotFound() {
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L))
                .thenReturn(Optional.empty());

        assertThrows(BorrowingRecordNotFoundException.class, () -> borrowingService.returnBook(1L, 1L));
    }
}

