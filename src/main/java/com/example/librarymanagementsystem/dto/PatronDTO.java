package com.example.librarymanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PatronDTO {
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
}
