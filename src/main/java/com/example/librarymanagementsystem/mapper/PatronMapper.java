package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.dto.PatronDTO;
import com.example.librarymanagementsystem.entity.Patron;

/**
 * Mapper class to convert between Patron and PatronDTO objects.
 */
public class PatronMapper {

    /**
     * Converts a Patron entity to a PatronDTO object.
     *
     * @param patron The Patron entity to be converted.
     * @return A PatronDTO object.
     */
    public static PatronDTO toPatronDTO(Patron patron) {
        return new PatronDTO(
            patron.getName(),
            patron.getPhoneNumber(),
            patron.getEmailAddress()
        );
    }

    /**
     * Converts a PatronDTO object to a Patron entity.
     *
     * @param patronDTO The PatronDTO object to be converted.
     * @return A Patron entity.
     */
    public static Patron toPatron(PatronDTO patronDTO) {
        Patron patron = new Patron();
        patron.setName(patronDTO.getName());
        patron.setPhoneNumber(patronDTO.getPhoneNumber());
        patron.setEmailAddress(patronDTO.getEmailAddress());
        return patron;
    }
}
