package com.example.librarymanagementsystem.serviceImpl;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.BorrowingRecord;
import com.example.librarymanagementsystem.entity.Patron;
import com.example.librarymanagementsystem.exceptionHandler.BookAlreadyBorrowedException;
import com.example.librarymanagementsystem.exceptionHandler.BorrowingRecordNotFoundException;
import com.example.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.example.librarymanagementsystem.service.BookService;
import com.example.librarymanagementsystem.service.BorrowingService;
import com.example.librarymanagementsystem.service.PatronService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;
    private final PatronService patronService;

    @Autowired
    public BorrowingServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookService bookService, PatronService patronService) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookService = bookService;
        this.patronService = patronService;
    }

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
                .findByBookIdAndPatronIdAndReturnDateTimeIsNull(bookId, patronId);
        BorrowingRecord borrowingRecord = optionalBorrowingRecord.orElseThrow(() ->
                new BorrowingRecordNotFoundException("Borrowing record not found for bookId: "
                        + bookId + " and patronId: " + patronId));

        borrowingRecord.setReturnDateTime(LocalDateTime.now());
        borrowingRecordRepository.save(borrowingRecord);

        Book book = borrowingRecord.getBook();
        book.setBorrowed(false);
        bookService.updateBook(bookId, book);
    }
}