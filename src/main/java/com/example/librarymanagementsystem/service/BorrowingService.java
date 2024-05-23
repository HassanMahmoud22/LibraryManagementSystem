package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.exceptionHandler.BookAlreadyBorrowedException;
import com.example.librarymanagementsystem.exceptionHandler.BorrowingRecordNotFoundException;
import com.example.librarymanagementsystem.exceptionHandler.PatronNotFoundException;
import jakarta.validation.constraints.NotNull;

/**
 * Service interface for managing borrowing operations.
 * This interface provides methods for borrowing and returning books in the library management system.
 */
public interface BorrowingService {

    /**
     * Borrow a book from the library.
     *
     * @param bookId   The ID of the book to borrow
     * @param patronId The ID of the patron borrowing the book
     * @throws BookAlreadyBorrowedException   if the book is already borrowed
     * @throws PatronNotFoundException        if the patron is not found
     * @throws BorrowingRecordNotFoundException if the borrowing record is not found
     */
    void borrowBook(@NotNull Long bookId, @NotNull Long patronId);

    /**
     * Return a borrowed book to the library.
     *
     * @param bookId   The ID of the book to return
     * @param patronId The ID of the patron returning the book
     * @throws BorrowingRecordNotFoundException if the borrowing record is not found
     */
    void returnBook(Long bookId, Long patronId);
}