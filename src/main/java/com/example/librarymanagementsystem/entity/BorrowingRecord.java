package com.example.librarymanagementsystem.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Data
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Patron patron;

    private LocalDate borrowDate;
    private LocalDate returnDate;
    public BorrowingRecord() {}
    public BorrowingRecord(Book book, Patron patron) {
        this.book = book;
        this.patron = patron;
        this.borrowDate = LocalDate.now(); // Set the borrow date to the current date
    }
}