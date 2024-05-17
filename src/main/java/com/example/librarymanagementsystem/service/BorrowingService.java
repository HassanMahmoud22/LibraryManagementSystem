package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.BorrowingRecord;
import com.example.librarymanagementsystem.entity.Patron;
import com.example.librarymanagementsystem.exceptionHandler.BookAlreadyBorrowedException;
import com.example.librarymanagementsystem.exceptionHandler.BookNotFoundException;
import com.example.librarymanagementsystem.exceptionHandler.BorrowingRecordNotFoundException;
import com.example.librarymanagementsystem.exceptionHandler.PatronNotFoundException;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.example.librarymanagementsystem.repository.PatronRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private PatronService patronService;

    @Transactional
    public void borrowBook(@NotNull Long bookId, @NotNull Long patronId) {
        Book book = bookService.getBookById(bookId);

        if (book.isBorrowed()) {
            throw new BookAlreadyBorrowedException("Book with id " + bookId + " is already borrowed.");
        }

        Patron patron = patronService.getPatronById(patronId);
        BorrowingRecord borrowingRecord = new BorrowingRecord(book, patron);
        book.setBorrowed(true);
        bookService.updateBook(bookId, book);
        borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public void returnBook(Long bookId, Long patronId) {
        Optional<BorrowingRecord> optionalBorrowingRecord = borrowingRecordRepository
                .findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);
        BorrowingRecord borrowingRecord = optionalBorrowingRecord.orElseThrow(() ->
                new BorrowingRecordNotFoundException("Borrowing record not found for bookId: "
                        + bookId + " and patronId: " + patronId));

        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);

        // Update the borrowing status of the book to false
        Book book = borrowingRecord.getBook();
        book.setBorrowed(false);
        bookService.updateBook(bookId, book);
    }
}

