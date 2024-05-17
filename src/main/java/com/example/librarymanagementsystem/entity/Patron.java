package com.example.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name mustn't be blank")
    @NotNull (message = "name mustn't be null")
    @JsonProperty (required = true)
    @NotEmpty (message = "name mustn't be empty")
    @Pattern (regexp = "^[a-zA-Z\\s]+$", message = "Only letters and spaces are allowed for name")
    private String name;

    @NotBlank(message = "phoneNumber mustn't be blank")
    @NotNull (message = "phoneNumber mustn't be null")
    @JsonProperty (required = true)
    @NotEmpty (message = "phoneNumber mustn't be empty")
    @Pattern (regexp="\\d{10}", message="Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "emailAddress mustn't be blank")
    @NotNull (message = "emailAddress mustn't be null")
    @Email
    private String emailAddress;

    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL)
    private Set<BorrowingRecord> borrowingRecords;
}