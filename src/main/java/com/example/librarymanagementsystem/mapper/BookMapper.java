package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.dto.BookDTO;
import com.example.librarymanagementsystem.entity.Book;

public class BookMapper {

    public static BookDTO toBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublicationYear(book.getPublicationYear());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setBorrowed(book.isBorrowed());
        return bookDTO;
    }

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
