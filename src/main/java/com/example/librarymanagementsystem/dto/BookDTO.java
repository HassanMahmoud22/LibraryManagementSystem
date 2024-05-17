package com.example.librarymanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import jakarta.validation.constraints.*;


@Data
public class BookDTO {

    @NotBlank (message = "Title mustn't be blank")
    @NotNull (message = "Title is required")
    @JsonProperty (required = true)
    @NotEmpty (message = "Title mustn't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Only letters, numbers, and spaces are allowed for title")
    private String title;

    @NotBlank(message = "Author is required")
    @NotNull(message = "Author is required")
    @JsonProperty (required = true)
    @NotEmpty(message = "Author mustn't be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Only letters and spaces are allowed for title")
    private String author;

    @Min (value = 1700, message = "Publication year must be greater than or equal to 1700")
    @Max (value = 2024, message = "Publication year must be less than or equal to 2024")
    @NotNull(message = "publication year is required")
    @JsonProperty (required = true)
    private int publicationYear;

    @NotBlank(message = "ISBN is required")
    @NotNull(message = "ISBN is required")
    @JsonProperty (required = true)
    @NotEmpty(message = "ISBN mustn't be empty")
    @Pattern (regexp = "\\d{3}-\\d{10}", message = "ISBN must be in the format xxx-xxxxxxxxxx")
    private String isbn;

    @JsonIgnore
    private boolean borrowed;
}
