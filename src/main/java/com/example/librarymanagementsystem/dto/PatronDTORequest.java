package com.example.librarymanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing a patron (library user).
 * This class is used for transferring patron data between the client and the server.
 */
@Data
public class PatronDTORequest {

    @NotBlank(message = "Name mustn't be blank")
    @NotNull(message = "Name mustn't be null")
    @JsonProperty(required = true)
    @NotEmpty(message = "Name mustn't be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Only letters and spaces are allowed for name")
    private String name;

    @NotBlank(message = "Phone number mustn't be blank")
    @NotNull(message = "Phone number mustn't be null")
    @JsonProperty(required = true)
    @NotEmpty(message = "Phone number mustn't be empty")
    @Pattern(regexp="\\d{10}", message="Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Email address mustn't be blank")
    @NotNull(message = "Email address mustn't be null")
    @Email(message = "Invalid email address")
    private String emailAddress;

    public PatronDTORequest(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }
}
