package com.example.librarymanagementsystem.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.librarymanagementsystem.entity.Patron;
import com.example.librarymanagementsystem.exceptionHandler.PatronNotFoundException;
import com.example.librarymanagementsystem.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PatronServiceImplTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllPatrons() {
        Patron patron1 = new Patron(1L, "Patron1", "1234567890", "patron1@example.com");
        Patron patron2 = new Patron(2L, "Patron2", "0987654321", "patron2@example.com");

        when(patronRepository.findAll()).thenReturn(Arrays.asList(patron1, patron2));

        List<Patron> patrons = patronService.getAllPatrons();

        assertEquals(2, patrons.size());
        verify(patronRepository, times(1)).findAll();
    }

    @Test
    void testGetPatronById_Success() {
        Patron patron = new Patron(1L, "Patron1", "1234567890", "patron1@example.com");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        Patron foundPatron = patronService.getPatronById(1L);

        assertEquals("Patron1", foundPatron.getName());
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPatronById_NotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> patronService.getPatronById(1L));
    }

    @Test
    void testAddPatron_Success() {
        Patron patron = new Patron(1L, "Patron1", "1234567890", "patron1@example.com");

        when(patronRepository.save(any(Patron.class))).thenReturn(patron);

        Patron addedPatron = patronService.addPatron(patron);

        assertEquals("Patron1", addedPatron.getName());
        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    void testUpdatePatron_Success() {
        Patron existingPatron = new Patron(1L, "Patron1", "1234567890", "patron1@example.com");
        Patron updatedPatronDetails = new Patron(1L, "UpdatedPatron", "0987654321", "updatedpatron@example.com");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(existingPatron));
        when(patronRepository.save(any(Patron.class))).thenReturn(updatedPatronDetails);

        Patron updatedPatron = patronService.updatePatron(1L, updatedPatronDetails);

        assertEquals("UpdatedPatron", updatedPatron.getName());
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).save(existingPatron);
    }

    @Test
    void testUpdatePatron_NotFound() {
        Patron updatedPatronDetails = new Patron(1L, "UpdatedPatron", "0987654321", "updatedpatron@example.com");

        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> patronService.updatePatron(1L, updatedPatronDetails));
    }

    @Test
    void testDeletePatron_Success() {
        Patron patron = new Patron(1L, "Patron1", "1234567890", "patron1@example.com");

        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        patronService.deletePatron(1L);

        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).delete(patron);
    }

    @Test
    void testDeletePatron_NotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> patronService.deletePatron(1L));
    }
}
