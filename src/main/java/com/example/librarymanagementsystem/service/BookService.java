package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.exceptionHandler.BookISBNAlreadyExistsException;
import com.example.librarymanagementsystem.exceptionHandler.BookNotFoundException;

import java.util.List;

/**
 * Interface for managing books.
 */
public interface BookService {

    /**
     * Retrieves all books from the repository.
     *
     * @return List of all books.
     */
    List<Book> getAllBooks();

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The book with the specified ID.
     * @throws BookNotFoundException If the book with the given ID does not exist.
     */
    Book getBookById(Long id);

    /**
     * Adds a new book to the repository.
     *
     * @param book The book to add.
     * @return The added book.
     * @throws BookISBNAlreadyExistsException If the ISBN of the book already exists in the repository.
     */
    Book addBook(Book book);

    /**
     * Updates the details of an existing book.
     *
     * @param id          The ID of the book to update.
     * @param bookDetails The updated details of the book.
     * @return The updated book.
     * @throws BookNotFoundException If the book with the given ID does not exist.
     */
    Book updateBook(Long id, Book bookDetails);

    /**
     * Deletes a book from the repository.
     *
     * @param id The ID of the book to delete.
     * @throws BookNotFoundException If the book with the given ID does not exist.
     */
    void deleteBook(Long id);
}
