package com.example.librarymanagementsystem.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.CONFLICT)
public class BookISBNAlreadyExistsException extends RuntimeException {
    private String message;

    public BookISBNAlreadyExistsException() {}
    public BookISBNAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
