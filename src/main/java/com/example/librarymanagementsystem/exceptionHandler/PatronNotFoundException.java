package com.example.librarymanagementsystem.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to be thrown when a patron is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatronNotFoundException extends RuntimeException {

    /**
     * Constructs a new PatronNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public PatronNotFoundException(String message) {
        super(message);
    }
}
