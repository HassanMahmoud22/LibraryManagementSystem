package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.BookDTO;
import com.example.librarymanagementsystem.mapper.BookMapper;
import com.example.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/api/books", produces = APPLICATION_JSON_VALUE)
@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return  ResponseEntity.ok().body(bookService.getAllBooks().stream()
                .map(BookMapper::toBookDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(value = "id") Long bookId) {
        return ResponseEntity.ok().body(
                BookMapper.toBookDTO(
                    bookService.getBookById(bookId)
                ));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBook( @RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            BookMapper.toBookDTO(
                bookService.addBook(BookMapper.toBook(bookDTO))
            )
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable(value = "id") Long bookId,
                                        @Valid @RequestBody BookDTO bookDetailsDTO) {

        return ResponseEntity.ok().body(
                BookMapper.toBookDTO(
                    bookService.updateBook(bookId, BookMapper.toBook(bookDetailsDTO))
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable(value = "id") Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}