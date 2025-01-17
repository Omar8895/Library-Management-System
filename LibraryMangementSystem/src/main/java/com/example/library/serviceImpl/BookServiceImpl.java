package com.example.library.serviceImpl;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.example.library.entities.Book;
import com.example.library.exceptions.BusinessLogicViolationException;
import com.example.library.helper.Helper;
import com.example.library.models.BookReqModel;
import com.example.library.models.BookResModel;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;


@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepositorey;
	
	@Cacheable("books")
	@Override
	public List<BookResModel> getAllBooks(int pageNo ,int pageSize) {
	
		Pageable page = PageRequest.of(pageNo, pageSize);
		Page<Book> allBooks = bookRepositorey.findAll(page);
		List<Book>listOfBooks=allBooks.getContent();
		return listOfBooks.stream().map( b -> Helper.mapToBookResModel(b)).collect(Collectors.toList());
		
	}
	
	

	@Override
	@Transactional
	public Integer createBook(BookReqModel bookReqModel) {
		
		return bookRepositorey.save(Helper.mapToBookReqModel(bookReqModel)).getId();

	}
	
	

	@Override
	public BookResModel getBookById(Integer id) {
		
		Book book = bookRepositorey.findById(id).orElseThrow(()->  new BusinessLogicViolationException("BOOK NOT FOUND"));
		return Helper.mapToBookResModel(book);	
	}
	
	

	@Override
	@Transactional
	public BookResModel updateBook(Integer id, BookReqModel bookReqModel) {
		
		Book book = bookRepositorey.findById(id).orElseThrow(()->  new BusinessLogicViolationException("BOOK NOT FOUND"));
		Helper.setDataToBook(book,bookReqModel);
		bookRepositorey.save(book);
		return Helper.mapToBookResModel(book);

	}

	@Override
	public void deleteBook(Integer id) {
	
		bookRepositorey.delete(bookRepositorey.findById(id).orElseThrow(()-> new BusinessLogicViolationException("BOOK NOT FOUND")));
	}
	
	

}
