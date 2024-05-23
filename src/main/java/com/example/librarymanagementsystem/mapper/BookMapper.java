package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.dto.BookDTORequest;
import com.example.librarymanagementsystem.dto.BookDTOResponse;
import com.example.librarymanagementsystem.entity.Book;

/**
 * Mapper class to convert between Book and BookDTORequest objects.
 */
public class BookMapper {

    /**
     * Converts a Book entity to a BookDTOResponse object.
     *
     * @param book The Book entity to be converted.
     * @return A BookDTOResponse object.
     */
    public static BookDTOResponse toBookDTOResponse(Book book) {
        return new BookDTOResponse(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getPublicationYear(),
            book.getIsbn(),
            book.isBorrowed()
        );
    }

    /**
     * Converts a BookDTORequest object to a Book entity.
     *
     * @param bookDTORequest The BookDTORequest object to be converted.
     * @return A Book entity.
     */
    public static Book toBook(BookDTORequest bookDTORequest) {
        return new Book(
            bookDTORequest.getTitle(),
            bookDTORequest.getAuthor(),
            bookDTORequest.getPublicationYear(),
            bookDTORequest.getIsbn(),
            bookDTORequest.isBorrowed()
        );
    }
}