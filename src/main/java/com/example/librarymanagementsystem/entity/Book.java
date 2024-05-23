package com.example.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;

/**
 * Entity class representing a book in the library management system.
 */
@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title mustn't be blank")
    @NotNull(message = "Title is required")
    @JsonProperty(required = true)
    @NotEmpty(message = "Title mustn't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Only letters, numbers, and spaces are allowed for title")
    private String title;

    @NotBlank(message = "Author is required")
    @NotNull(message = "Author is required")
    @JsonProperty(required = true)
    @NotEmpty(message = "Author mustn't be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Only letters and spaces are allowed for author")
    private String author;

    @Min(value = 1700, message = "Publication year must be greater than or equal to 1700")
    @Max(value = 2024, message = "Publication year must be less than or equal to 2024")
    @NotNull(message = "Publication year is required")
    @JsonProperty(required = true)
    private int publicationYear;

    @NotBlank(message = "ISBN is required")
    @NotNull(message = "ISBN is required")
    @JsonProperty(required = true)
    @NotEmpty(message = "ISBN mustn't be empty")
    @Pattern(regexp = "\\d{3}-\\d{10}", message = "ISBN must be in the format xxx-xxxxxxxxxx")
    @Column(unique = true)
    private String isbn;

    @JsonIgnore
    @Column(name = "borrowed", columnDefinition = "boolean default false")
    private boolean borrowed;

    public Book () {}

    public Book(Long id, String title, String author, int publicationYear, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }
    public Book(Long id, String title, String author, int publicationYear, String isbn, boolean borrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.borrowed = borrowed;
    }
}
