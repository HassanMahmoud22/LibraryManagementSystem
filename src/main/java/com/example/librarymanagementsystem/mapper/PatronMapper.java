package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.dto.PatronDTORequest;
import com.example.librarymanagementsystem.dto.PatronDTOResponse;
import com.example.librarymanagementsystem.entity.Patron;

/**
 * Mapper class to convert between Patron and PatronDTORequest objects.
 */
public class PatronMapper {

    /**
     * Converts a Patron entity to a PatronDTOResponse object.
     *
     * @param patron The Patron entity to be converted.
     * @return A PatronDTOResponse object.
     */
    public static PatronDTOResponse toPatronDTOResponse(Patron patron) {
        return new PatronDTOResponse(
            patron.getId(),
            patron.getName(),
            patron.getPhoneNumber(),
            patron.getEmailAddress()
        );
    }

    /**
     * Converts a PatronDTORequest object to a Patron entity.
     *
     * @param patronDTORequest The PatronDTORequest object to be converted.
     * @return A Patron entity.
     */
    public static Patron toPatron(PatronDTORequest patronDTORequest) {
        Patron patron = new Patron();
        patron.setName(patronDTORequest.getName());
        patron.setPhoneNumber(patronDTORequest.getPhoneNumber());
        patron.setEmailAddress(patronDTORequest.getEmailAddress());
        return patron;
    }
}