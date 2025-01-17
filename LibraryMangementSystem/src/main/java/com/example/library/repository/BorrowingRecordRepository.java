package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.library.entities.BorrowingRecords;


public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecords, Integer> {

	@Query(value="Select b from BorrowingRecords b where b.borrowedBook.id = ?1 and b.patronData.id =?2")
	BorrowingRecords findByBookIdAndPatronId(Integer bookdId , Integer PatronId);
	
}
