package com.example.library.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.library.entities.Book;
import com.example.library.entities.BorrowingRecords;
import com.example.library.entities.Patron;
import com.example.library.exceptions.BusinessLogicViolationException;
import com.example.library.helper.Helper;
import com.example.library.models.BorrowReqModel;
import com.example.library.models.ReturnReqModel;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowingRecordRepository;
import com.example.library.repository.PatronRepository;
import com.example.library.service.BorrowingRecordService;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService{

	@Autowired
	private BorrowingRecordRepository borrowingRecordRepositorey;
	
	@Autowired
	private BookRepository bookRepositorey;
	
	@Autowired
	private PatronRepository patronRepositorey;
	
	
	@Override
	@Transactional
	public Integer createBookBorrowigRecord(Integer bookId , Integer patronId , BorrowReqModel borrowReqModel) {
		
		Book book = bookRepositorey.findById(bookId).orElseThrow(()->  new BusinessLogicViolationException("BOOK NOT FOUND"));
		Patron patron = patronRepositorey.findById(patronId).orElseThrow(()->  new BusinessLogicViolationException("PATRON NOT FOUND"));
		return borrowingRecordRepositorey.save(Helper.mapToBorrowingRecords(borrowReqModel , book , patron)).getId();

	}
	
	

	@Override
	@Transactional
	public void createBookReturnRecord(Integer bookId, Integer patronId, ReturnReqModel returnReqModel) {
		
		Book book = bookRepositorey.findById(bookId).orElseThrow(()->  new BusinessLogicViolationException("BOOK NOT FOUND"));
		Patron patron = patronRepositorey.findById(patronId).orElseThrow(()->  new BusinessLogicViolationException("PATRON NOT FOUND"));
		
		BorrowingRecords oldRecord = borrowingRecordRepositorey.findByBookIdAndPatronId(bookId, patronId);
		
		if(oldRecord == null) {
			throw new BusinessLogicViolationException("RECORD NOT FOUND");
		}else {
			
			oldRecord.setReturnDate(returnReqModel.getReturnDate());
			borrowingRecordRepositorey.save(oldRecord);
	
		}
	}
	
	
	
}
