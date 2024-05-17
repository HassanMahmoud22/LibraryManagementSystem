package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.dto.PatronDTO;
import com.example.librarymanagementsystem.entity.Patron;

public class PatronMapper {

    public static PatronDTO toPatronDTO(Patron patron) {
        PatronDTO patronDTO = new PatronDTO();
        patronDTO.setName(patron.getName());
        patronDTO.setPhoneNumber(patron.getPhoneNumber());
        patronDTO.setEmailAddress(patron.getEmailAddress());
        return patronDTO;
    }

    public static Patron toPatron(PatronDTO patronDTO) {
        Patron patron = new Patron();
        System.out.println("patronDTO gwa elmapper "+patronDTO.getName());

        patron.setName(patronDTO.getName());
        System.out.println("patron gwa elmapper "+patron.getName());
        patron.setPhoneNumber(patronDTO.getPhoneNumber());
        patron.setEmailAddress(patronDTO.getEmailAddress());
        return patron;
    }
}
