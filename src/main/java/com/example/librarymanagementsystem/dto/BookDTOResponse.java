package com.example.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class BookDTOResponse {

    private long id;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private boolean isBorrowed;

    public BookDTOResponse(long id, String title, String author, int publicationYear, String isbn, boolean isBorrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.isBorrowed = isBorrowed;
    }
}

