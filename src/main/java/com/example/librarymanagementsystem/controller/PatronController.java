package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.PatronDTORequest;
import com.example.librarymanagementsystem.dto.PatronDTOResponse;
import com.example.librarymanagementsystem.mapper.PatronMapper;
import com.example.librarymanagementsystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

/**
 * Controller class for managing patron-related endpoints.
 * This class handles HTTP requests related to managing patrons in the library system.
 */
@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    /**
     * Handles GET request to retrieve all patrons.
     *
     * @return ResponseEntity containing a list of all patrons
     */
    @GetMapping
    public ResponseEntity<List<PatronDTOResponse>> getAllPatrons() {
        return  ResponseEntity.ok().body(patronService.getAllPatrons().stream()
                .map(PatronMapper::toPatronDTOResponse)
                .collect(Collectors.toList()));
    }

    /**
     * Handles GET request to retrieve a patron by ID.
     *
     * @param patronId the ID of the patron to retrieve
     * @return ResponseEntity containing the patron details
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatronDTOResponse> getPatronById(@PathVariable(value = "id") Long patronId) {
        return ResponseEntity.ok().body(
                PatronMapper.toPatronDTOResponse(
                        patronService.getPatronById(patronId)
                ));
    }

    /**
     * Handles POST request to add a new patron.
     *
     * @param patronDTORequest the DTO representing the patron to add
     * @return ResponseEntity containing the added patron details
     */
    @PostMapping
    public ResponseEntity<?> addPatron(@Valid @RequestBody PatronDTORequest patronDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                PatronMapper.toPatronDTOResponse(
                        patronService.addPatron(PatronMapper.toPatron(patronDTORequest))
                ));
    }

    /**
     * Handles PUT request to update an existing patron.
     *
     * @param patronId  the ID of the patron to update
     * @param patronDTORequest the DTO representing the updated patron details
     * @return ResponseEntity containing the updated patron details
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatron(@PathVariable(value = "id") Long patronId,
                                          @Valid @RequestBody PatronDTORequest patronDTORequest) {
        System.out.println(patronDTORequest.getName());
        return ResponseEntity.ok().body(
                PatronMapper.toPatronDTOResponse(
                        patronService.updatePatron(patronId, PatronMapper.toPatron(patronDTORequest))
                ));
    }

    /**
     * Handles DELETE request to delete a patron by ID.
     *
     * @param patronId the ID of the patron to delete
     * @return ResponseEntity indicating the success of the deletion operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable(value = "id") Long patronId) {
        patronService.deletePatron(patronId);
        return ResponseEntity.noContent().build();
    }
}
