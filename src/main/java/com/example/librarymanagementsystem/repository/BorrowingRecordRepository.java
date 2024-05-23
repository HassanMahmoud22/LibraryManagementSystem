package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on BorrowingRecord entities.
 */
@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    /**
     * Retrieves an optional BorrowingRecord entity by bookId, patronId, and with a null returnDate.
     *
     * @param bookId   The ID of the book.
     * @param patronId The ID of the patron.
     * @return An optional BorrowingRecord entity.
     */
    Optional<BorrowingRecord> findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
}
