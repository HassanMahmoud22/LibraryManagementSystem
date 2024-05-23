package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing borrowing-related endpoints.
 * This class handles HTTP requests related to borrowing books and returning books.
 */
@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    private final BorrowingService borrowingService;


    @Autowired
    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    /**
     * Handles POST request to borrow a book by a patron.
     *
     * @param bookId   the ID of the book to borrow
     * @param patronId the ID of the patron borrowing the book
     * @return ResponseEntity indicating the success of the borrowing operation
     */
    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<Void> borrowBook(@PathVariable Long bookId,
                                           @PathVariable Long patronId) {
        borrowingService.borrowBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Handles PUT request to return a borrowed book by a patron.
     *
     * @param bookId   the ID of the book to return
     * @param patronId the ID of the patron returning the book
     * @return ResponseEntity indicating the success of the return operation
     */
    @PutMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long bookId,
                                           @PathVariable Long patronId) {
        borrowingService.returnBook(bookId, patronId);
        return ResponseEntity.ok().build();
    }
}
