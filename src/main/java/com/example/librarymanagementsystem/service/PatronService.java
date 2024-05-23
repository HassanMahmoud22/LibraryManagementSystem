package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.entity.Patron;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface for managing patrons.
 */
@Service
public interface PatronService {

    /**
     * Retrieve all patrons.
     *
     * @return List of patrons.
     */
    List<Patron> getAllPatrons();

    /**
     * Retrieve a patron by ID.
     *
     * @param id The ID of the patron to retrieve.
     * @return The patron with the specified ID.
     */
    Patron getPatronById(Long id);

    /**
     * Add a new patron.
     *
     * @param patron The patron to add.
     * @return The added patron.
     */
    Patron addPatron(Patron patron);

    /**
     * Update details of an existing patron.
     *
     * @param id            The ID of the patron to update.
     * @param patronDetails Details of the patron to update.
     * @return The updated patron.
     */
    Patron updatePatron(Long id, Patron patronDetails);

    /**
     * Delete a patron by ID.
     *
     * @param id The ID of the patron to delete.
     */
    void deletePatron(Long id);
}
