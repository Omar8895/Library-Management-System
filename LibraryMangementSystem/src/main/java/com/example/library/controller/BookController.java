package com.example.library.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.models.BookReqModel;
import com.example.library.models.BookResModel;
import com.example.library.service.BookService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path="/api/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	
	@ApiOperation("Get All Books")
	@GetMapping
	public ResponseEntity<List<BookResModel>> getAllBooks(
			@RequestParam(value = "pageNo" ,defaultValue = "0" , required = false) int pageNo,
			@RequestParam(value = "pageSize" ,defaultValue = "5" , required = false) int pageSize
			) {
		return new ResponseEntity<>(bookService.getAllBooks(pageNo,pageSize), HttpStatus.OK);
	}
	
	@ApiOperation("Create New Book")
	@PostMapping
	public ResponseEntity<Integer> createBook(
			@ApiParam("Book Req Model") @Valid @RequestBody BookReqModel bookReqModel) {
		return new ResponseEntity<>(bookService.createBook(bookReqModel), HttpStatus.OK);
	}
	
	@ApiOperation("Get Book By Id")
	@GetMapping(path = "/{id}")
	public ResponseEntity<BookResModel> getBookById(
			@ApiParam("Book Id") @PathVariable("id") Integer booktId) {
		return new ResponseEntity<>(bookService.getBookById(booktId), HttpStatus.OK);
	}
	
	@ApiOperation("Update Book")
	@PutMapping(path = "/{id}")
	public ResponseEntity<BookResModel> updateBook(@ApiParam("Book Id") @PathVariable("id") Integer bookId,
			@ApiParam("Book Request Model") @Valid @RequestBody BookReqModel bookReqModel) {
		return new ResponseEntity<>(bookService.updateBook(bookId, bookReqModel),
				HttpStatus.OK);
	}
	
	@ApiOperation("Delete Book")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteBook(@ApiParam("Book Id") @PathVariable("id") Integer bookId) {	
		bookService.deleteBook(bookId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
