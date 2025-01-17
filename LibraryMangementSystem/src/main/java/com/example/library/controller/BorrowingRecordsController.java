package com.example.library.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.library.models.BorrowReqModel;
import com.example.library.models.ReturnReqModel;
import com.example.library.service.BorrowingRecordService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path="/api/borrow")
public class BorrowingRecordsController {
	
	@Autowired
	private BorrowingRecordService borrowingRecordService;
	
	@ApiOperation("Create New Book Borrowing Record")
	@PostMapping(path = "/{bookId}/patron/{patronId}")
	public ResponseEntity<Integer> createBookBorrowigRecord(
			@ApiParam("Book Id") @PathVariable("bookId") Integer bookId,
			@ApiParam("Patron Id") @PathVariable("patronId") Integer patronId,
			@ApiParam("Borrow Req Model") @Valid @RequestBody BorrowReqModel borrowReqModel) {
		return new ResponseEntity<>(borrowingRecordService.createBookBorrowigRecord(bookId,patronId,borrowReqModel), HttpStatus.OK);
	}
	
	
	@ApiOperation("Create New Book Return Record")
	@PutMapping(path = "/{bookId}/patron/{patronId}")
	public ResponseEntity<Void> createBookReturnRecord(
			@ApiParam("Book Id") @PathVariable("bookId") Integer bookId,
			@ApiParam("Patron Id") @PathVariable("patronId") Integer patronId,
			@ApiParam("Borrow Req Model") @Valid @RequestBody ReturnReqModel returnReqModel) {
		borrowingRecordService.createBookReturnRecord(bookId,patronId,returnReqModel);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
