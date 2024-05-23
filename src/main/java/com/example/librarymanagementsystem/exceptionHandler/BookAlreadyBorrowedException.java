package com.example.librarymanagementsystem.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that the book is already borrowed when trying to borrow it again.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookAlreadyBorrowedException extends RuntimeException {
    /**
     * Constructs a new BookAlreadyBorrowedException with the specified detail message.
     *
     * @param message the detail message
     */
    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}
