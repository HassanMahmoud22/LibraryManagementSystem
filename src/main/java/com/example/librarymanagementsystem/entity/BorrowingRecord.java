package com.example.librarymanagementsystem.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing a borrowing record in the library management system.
 */
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

    private LocalDateTime borrowDateTime;
    private LocalDateTime returnDateTime;

    public BorrowingRecord() {}

    public BorrowingRecord(Book book, Patron patron) {
        this.book = book;
        this.patron = patron;
        this.borrowDateTime = LocalDateTime.now();
    }
}
