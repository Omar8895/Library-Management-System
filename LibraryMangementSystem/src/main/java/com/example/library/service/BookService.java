package com.example.library.service;

import java.util.List;

import com.example.library.models.BookReqModel;
import com.example.library.models.BookResModel;

public interface BookService {
	
	List<BookResModel>getAllBooks(int pageNo , int pageSize);
	
	Integer createBook(BookReqModel bookReqModel);
	
	BookResModel getBookById(Integer id);
	
	BookResModel updateBook (Integer id , BookReqModel bookReqModel);
	
	void deleteBook (Integer id);

}
