package com.example.librarymanagementsystem.serviceImpl;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.exceptionHandler.BookISBNAlreadyExistsException;
import com.example.librarymanagementsystem.exceptionHandler.BookNotFoundException;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;

/**
 * Implementation of the BookService interface.
 * Provides methods to manage books in the library management system.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books stored in the system.
     *
     * @return List of all books in the system
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve
     * @return The book with the specified ID
     * @throws BookNotFoundException if no book exists with the given ID
     */
    @Cacheable(value = "books", key = "#id")
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    /**
     * Adds a new book to the system.
     *
     * @param book The book to add
     * @return The added book
     * @throws BookISBNAlreadyExistsException if the ISBN of the book already exists
     */
    public Book addBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new BookISBNAlreadyExistsException("Book ISBN Already Exists");
        }
    }

    /**
     * Updates an existing book in the system.
     *
     * @param id          The ID of the book to update
     * @param bookDetails The updated details of the book
     * @return The updated book
     * @throws BookNotFoundException if no book exists with the given ID
     */
    @CachePut(value = "books", key = "#id")
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        updateBookDetails(book, bookDetails);
        return bookRepository.save(book);
    }

    /**
     * Deletes a book from the system.
     *
     * @param id The ID of the book to delete
     * @throws BookNotFoundException if no book exists with the given ID
     */
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    /**
     * Updates the details of a book with the details provided.
     *
     * @param book        The book to update
     * @param bookDetails The updated details of the book
     */
    private void updateBookDetails(Book book, Book bookDetails) {
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublicationYear(bookDetails.getPublicationYear());
        book.setIsbn(bookDetails.getIsbn());
    }
}