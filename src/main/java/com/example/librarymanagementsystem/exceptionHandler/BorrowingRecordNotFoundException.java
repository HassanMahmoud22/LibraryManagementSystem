package com.example.librarymanagementsystem.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that a borrowing record with the specified criteria could not be found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BorrowingRecordNotFoundException extends RuntimeException {
    /**
     * Constructs a new BorrowingRecordNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public BorrowingRecordNotFoundException(String message) {
        super(message);
    }
}
