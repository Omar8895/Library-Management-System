package com.example.library.service;

import com.example.library.models.BorrowReqModel;
import com.example.library.models.ReturnReqModel;

public interface BorrowingRecordService {

	Integer createBookBorrowigRecord(Integer bookId , Integer patronId , BorrowReqModel borrowReqModel);
	
	void createBookReturnRecord(Integer bookId , Integer patronId , ReturnReqModel returnReqModel);
}
