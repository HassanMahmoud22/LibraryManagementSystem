package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.PatronDTO;
import com.example.librarymanagementsystem.mapper.PatronMapper;
import com.example.librarymanagementsystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public ResponseEntity<List<PatronDTO>> getAllPatrons() {
        return  ResponseEntity.ok().body(patronService.getAllPatrons().stream()
                .map(PatronMapper::toPatronDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronDTO> getPatronById(@PathVariable(value = "id") Long patronId) {
        return ResponseEntity.ok().body(
                PatronMapper.toPatronDTO(
                        patronService.getPatronById(patronId)
                ));
    }

    @PostMapping
    public ResponseEntity<?> addPatron(@Valid @RequestBody PatronDTO patronDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                PatronMapper.toPatronDTO(
                        patronService.addPatron(PatronMapper.toPatron(patronDTO))
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatron(@PathVariable(value = "id") Long patronId,
                                          @Valid @RequestBody PatronDTO patronDTO) {
        System.out.println(patronDTO.getName());
        return ResponseEntity.ok().body(
                PatronMapper.toPatronDTO(
                        patronService.updatePatron(patronId, PatronMapper.toPatron(patronDTO))
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable(value = "id") Long patronId) {
        patronService.deletePatron(patronId);
        return ResponseEntity.noContent().build();
    }
}
