package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.BookDTORequest;
import com.example.librarymanagementsystem.dto.BookDTOResponse;
import com.example.librarymanagementsystem.mapper.BookMapper;
import com.example.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller class for managing book-related endpoints.
 * This class handles HTTP requests related to books, such as getting all books, adding, updating, and deleting books.
 */
@RequestMapping(path = "/api/books", produces = APPLICATION_JSON_VALUE)
@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Handles GET request to fetch all books.
     *
     * @return ResponseEntity containing a list of BookDTOs representing all books in the system
     */
    @GetMapping
    public ResponseEntity<List<BookDTOResponse>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.getAllBooks().stream()
                .map(BookMapper::toBookDTOResponse)
                .collect(Collectors.toList()));
    }

    /**
     * Handles GET request to fetch a book by its ID.
     *
     * @param bookId the ID of the book to fetch
     * @return ResponseEntity containing the BookDTORequest representing the requested book
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDTOResponse> getBookById(@PathVariable(value = "id") Long bookId) {
        return ResponseEntity.ok().body(
                BookMapper.toBookDTOResponse(
                        bookService.getBookById(bookId)
                ));
    }

    /**
     * Handles POST request to add a new book.
     *
     * @param bookDTORequest the BookDTORequest containing information about the book to add
     * @return ResponseEntity containing the created BookDTORequest representing the added book
     */
    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody @Valid BookDTORequest bookDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BookMapper.toBookDTOResponse(
                        bookService.addBook(BookMapper.toBook(bookDTORequest))
                )
        );
    }

    /**
     * Handles PUT request to update an existing book.
     *
     * @param bookId         the ID of the book to update
     * @param bookDetailsDTO the BookDTORequest containing updated information about the book
     * @return ResponseEntity containing the updated BookDTORequest representing the updated book
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable(value = "id") Long bookId,
                                        @Valid @RequestBody BookDTORequest bookDetailsDTO) {

        return ResponseEntity.ok().body(
                BookMapper.toBookDTOResponse(
                        bookService.updateBook(bookId, BookMapper.toBook(bookDetailsDTO))
                ));
    }

    /**
     * Handles DELETE request to delete a book by its ID.
     *
     * @param bookId the ID of the book to delete
     * @return ResponseEntity indicating the success of the deletion operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable(value = "id") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
