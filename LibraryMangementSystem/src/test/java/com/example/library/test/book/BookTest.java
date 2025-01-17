package com.example.library.test.book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.library.entities.Book;
import com.example.library.exceptions.BusinessLogicViolationException;
import com.example.library.models.BookReqModel;
import com.example.library.models.BookResModel;
import com.example.library.repository.BookRepository;
import com.example.library.serviceImpl.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookTest {

	@InjectMocks
	private BookServiceImpl bookService;

	@Mock
	private BookRepository bookRepositorey;

	@Test
	void getAllBooksTest() {
		int pageNo = 0;
		int pageSize = 5;
		Pageable pageable = PageRequest.of(pageNo, pageSize);

		Book book1 = new Book();
		book1.setId(1);
		book1.setTitle("Book One");
		book1.setAuthor("Author One");

		Book book2 = new Book();
		book2.setId(2);
		book2.setTitle("Book Two");
		book2.setAuthor("Author Two");

		List<Book> books = Arrays.asList(book1, book2);
		Page<Book> page = new PageImpl<>(books, pageable, books.size());

		// Mock repository methods
		when(bookRepositorey.findAll(pageable)).thenReturn(page);

		// Call the service method
		List<BookResModel> result = bookService.getAllBooks(pageNo, pageSize);

		// Assert the record is updated
		assertThat(result).hasSize(books.size());
		assertThat(result.get(0).getTitle()).isEqualTo(book1.getTitle());
		assertThat(result.get(1).getTitle()).isEqualTo(book2.getTitle());
	}

	@Test
	void getBookByIdTest() {

		int bookId = 1;

		// Mock repository methods
		when(bookRepositorey.findById(bookId)).thenReturn(Optional.empty());

		// Assert that a BusinessLogicViolationException is thrown
		assertThrows(BusinessLogicViolationException.class, () -> {
			bookService.getBookById(bookId);
		});
	}

	@Test
	void createBookTest() {

		BookReqModel bookReqModel = new BookReqModel();
		bookReqModel.setTitle("New Book");
		bookReqModel.setAuthor("New Author");
		bookReqModel.setPublicationYear(LocalDate.of(1995, 1, 1));
		bookReqModel.setIsbn("1234");

		Book book = new Book();
		book.setTitle(bookReqModel.getTitle());
		book.setAuthor(bookReqModel.getAuthor());
		book.setPublicationYear(bookReqModel.getPublicationYear());
		book.setIsbn(bookReqModel.getIsbn());

		// Mock repository methods
		when(bookRepositorey.save(book)).thenReturn(book);

		 // Call the service method
		Integer savedBookId = bookService.createBook(bookReqModel);

		// Verify interactions
		verify(bookRepositorey).save(book);
		
		// Assert the record is created
		assertThat(savedBookId).isEqualTo(book.getId());
	}

	@Test
	void updateBookTest() {

		Integer bookId = 1;

		BookReqModel bookReqModel = new BookReqModel();
		bookReqModel.setTitle("Updated Book Title");
		bookReqModel.setAuthor("Updated Author");
		bookReqModel.setPublicationYear(LocalDate.of(2000, 1, 1));
		bookReqModel.setIsbn("1234");

		Book existingBook = new Book();
		existingBook.setId(bookId);
		existingBook.setTitle("Old Title");
		existingBook.setAuthor("Old Author");
		existingBook.setPublicationYear(LocalDate.of(1990, 1, 1));

		Book updatedBook = new Book();
		updatedBook.setId(bookId);
		updatedBook.setTitle(bookReqModel.getTitle());
		updatedBook.setAuthor(bookReqModel.getAuthor());
		updatedBook.setPublicationYear(bookReqModel.getPublicationYear());
		updatedBook.setIsbn(bookReqModel.getIsbn());

		BookResModel bookResModel = new BookResModel();
		bookResModel.setTitle(updatedBook.getTitle());
		bookResModel.setAuthor(updatedBook.getAuthor());
		bookResModel.setPublicationYear(updatedBook.getPublicationYear());
		bookResModel.setIsbn(updatedBook.getIsbn());

		// Mock repository methods
		when(bookRepositorey.findById(bookId)).thenReturn(Optional.of(existingBook));
		when(bookRepositorey.save(any(Book.class))).thenReturn(updatedBook);

		 // Call the service method
		BookResModel result = bookService.updateBook(bookId, bookReqModel);

		// Verify interactions
		verify(bookRepositorey).findById(bookId);
		verify(bookRepositorey).save(updatedBook);
		
		// Assert the record is updated
		assertThat(result).isEqualTo(bookResModel);
	}

	@Test
	void updateBookNotFoundTest() {
		Integer bookId = 1;

		BookReqModel bookReqModel = new BookReqModel();
		bookReqModel.setTitle("Updated Book Title");
		bookReqModel.setAuthor("Updated Author");
		bookReqModel.setPublicationYear(LocalDate.of(2000, 1, 1));
		bookReqModel.setIsbn("1234");

		// Mock repository methods
		when(bookRepositorey.findById(bookId)).thenReturn(Optional.empty());

		// Assert that a BusinessLogicViolationException is thrown
		assertThrows(BusinessLogicViolationException.class, () -> {
			bookService.updateBook(bookId, bookReqModel);
		});
	}

	@Test
	void deleteBookTest() {

		Integer bookId = 1;

		Book book = new Book();
		book.setId(bookId);

		// Mock repository methods
		when(bookRepositorey.findById(bookId)).thenReturn(Optional.of(book));

		 // Call the service method
		bookService.deleteBook(bookId);

		// Verify interactions
		verify(bookRepositorey).findById(bookId);
		verify(bookRepositorey).delete(book);
	}

	@Test
	void deleteBookNotFoundTest() {
		
		Integer bookId = 1;

		// Mock repository methods
		when(bookRepositorey.findById(bookId)).thenReturn(Optional.empty());

		// Assert the record is deleted
		assertThrows(BusinessLogicViolationException.class, () -> {
			bookService.deleteBook(bookId);
		});
	}
}
