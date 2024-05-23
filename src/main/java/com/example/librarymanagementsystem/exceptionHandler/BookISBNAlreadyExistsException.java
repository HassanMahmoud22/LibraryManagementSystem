package com.example.librarymanagementsystem.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that a book with the same ISBN already exists in the system.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BookISBNAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new BookISBNAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public BookISBNAlreadyExistsException(String message) {
        super(message);
    }
}