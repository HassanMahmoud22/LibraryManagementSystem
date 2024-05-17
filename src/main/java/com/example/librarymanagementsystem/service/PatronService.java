package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.Patron;
import com.example.librarymanagementsystem.exceptionHandler.PatronNotFoundException;
import com.example.librarymanagementsystem.repository.PatronRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable (value = "patrons", key = "#id")
    public Patron getPatronById(Long id) {
        return getPatronEntityById(id);
    }

    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron updatePatron(Long id, Patron patronDetails) {
        Patron patron = getPatronById(id);
        updatePatronDetails(patron, patronDetails);
        return patronRepository.save(patron);
    }

    private void updatePatronDetails(Patron patron, Patron patronDetails) {
       patron.setName(patronDetails.getName());
       patron.setPhoneNumber(patronDetails.getPhoneNumber());
       patron.setEmailAddress(patron.getEmailAddress());
    }

    public void deletePatron(Long id) {
        Patron patron = getPatronEntityById(id);
        patronRepository.delete(patron);
    }

    private Patron getPatronEntityById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found with id: " + id));
    }
}