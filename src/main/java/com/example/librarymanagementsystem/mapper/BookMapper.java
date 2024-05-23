package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.dto.BookDTO;
import com.example.librarymanagementsystem.entity.Book;

/**
 * Mapper class to convert between Book and BookDTO objects.
 */
public class BookMapper {

    /**
     * Converts a Book entity to a BookDTO object.
     *
     * @param book The Book entity to be converted.
     * @return A BookDTO object.
     */
    public static BookDTO toBookDTO(Book book) {
        return new BookDTO(
            book.getTitle(),
            book.getAuthor(),
            book.getPublicationYear(),
            book.getIsbn(),
            book.isBorrowed()
        );
    }

    /**
     * Converts a BookDTO object to a Book entity.
     *
     * @param bookDTO The BookDTO object to be converted.
     * @return A Book entity.
     */
    public static Book toBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setIsbn(bookDTO.getIsbn());
        book.setBorrowed(bookDTO.isBorrowed());
        return book;
    }
}
