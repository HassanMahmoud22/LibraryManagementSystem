package com.example.librarymanagementsystem.serviceImpl;

import com.example.librarymanagementsystem.entity.Patron;
import com.example.librarymanagementsystem.exceptionHandler.PatronNotFoundException;
import com.example.librarymanagementsystem.repository.PatronRepository;
import com.example.librarymanagementsystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link PatronService} interface.
 */
@Service
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;

    @Autowired
    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    /**
     * Retrieve all patrons.
     *
     * @return List of patrons.
     */
    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    /**
     * Retrieve a patron by ID.
     *
     * @param id The ID of the patron to retrieve.
     * @return The patron with the specified ID.
     * @throws PatronNotFoundException If no patron is found with the given ID.
     */
    @Override
    @Cacheable(value = "patrons", key = "#id")
    public Patron getPatronById(Long id) {
        return getPatronEntityById(id);
    }

    /**
     * Add a new patron.
     *
     * @param patron The patron to add.
     * @return The added patron.
     */
    @Override
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    /**
     * Update details of an existing patron.
     *
     * @param id            The ID of the patron to update.
     * @param patronDetails Details of the patron to update.
     * @return The updated patron.
     * @throws PatronNotFoundException If no patron is found with the given ID.
     */
    @Override
    public Patron updatePatron(Long id, Patron patronDetails) {
        Patron patron = getPatronById(id);
        updatePatronDetails(patron, patronDetails);
        return patronRepository.save(patron);
    }

    /**
     * Delete a patron by ID.
     *
     * @param id The ID of the patron to delete.
     * @throws PatronNotFoundException If no patron is found with the given ID.
     */
    @Override
    public void deletePatron(Long id) {
        Patron patron = getPatronEntityById(id);
        patronRepository.delete(patron);
    }

    /**
     * Update details of a patron.
     *
     * @param patron         The patron to update.
     * @param patronDetails  Details of the patron to update.
     */
    private void updatePatronDetails(Patron patron, Patron patronDetails) {
        patron.setName(patronDetails.getName());
        patron.setPhoneNumber(patronDetails.getPhoneNumber());
        patron.setEmailAddress(patron.getEmailAddress());
    }

    /**
     * Retrieve a patron entity by ID.
     *
     * @param id The ID of the patron to retrieve.
     * @return The patron entity with the specified ID.
     * @throws PatronNotFoundException If no patron is found with the given ID.
     */
    private Patron getPatronEntityById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found with id: " + id));
    }
}
