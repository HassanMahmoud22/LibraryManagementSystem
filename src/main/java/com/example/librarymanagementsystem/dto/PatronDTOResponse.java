package com.example.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class PatronDTOResponse{

    private long id;
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public PatronDTOResponse(long id, String name, String phoneNumber, String emailAddress) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }
}
