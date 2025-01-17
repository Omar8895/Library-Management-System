package com.example.library.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.library.entities.Book;
import com.example.library.entities.BorrowingRecords;
import com.example.library.entities.Patron;
import com.example.library.entities.User;
import com.example.library.enums.ApiErrorEnum;
import com.example.library.exceptions.BusinessLogicViolationException;
import com.example.library.models.AuthenticationReqModel;
import com.example.library.models.BookReqModel;
import com.example.library.models.BookResModel;
import com.example.library.models.BorrowReqModel;
import com.example.library.models.PatronsReqModel;
import com.example.library.models.PatronsResModel;
import com.example.library.models.RegisterReqModel;

public class Helper {


	public static BookResModel mapToBookResModel(Book book) {

		BookResModel bookResModel = new BookResModel();
		bookResModel.setAuthor(book.getAuthor());
		bookResModel.setTitle(book.getTitle());
		bookResModel.setPublicationYear(book.getPublicationYear());
		bookResModel.setIsbn(book.getIsbn());
		return bookResModel;
	}

	public static Book mapToBookReqModel(BookReqModel bookReqModel) {

		Book newBook = new Book();
		newBook.setAuthor(bookReqModel.getAuthor());
		newBook.setTitle(bookReqModel.getTitle());
		newBook.setPublicationYear(bookReqModel.getPublicationYear());
		newBook.setIsbn(bookReqModel.getIsbn());
		return newBook;
	}

	public static void setDataToBook(Book book, BookReqModel bookReqModel) {

		book.setAuthor(bookReqModel.getAuthor());
		book.setTitle(bookReqModel.getTitle());
		book.setPublicationYear(bookReqModel.getPublicationYear());
		book.setIsbn(bookReqModel.getIsbn());
	}

	public static PatronsResModel mapToPatronsResModel(Patron patron) {

		PatronsResModel patronResModel = new PatronsResModel();
		patronResModel.setName(patron.getName());
		patronResModel.setMobile(patron.getMobile());
		patronResModel.setEmail(patron.getEmail());
		return patronResModel;
	}

	public static Patron mapToPatronsReqModel(PatronsReqModel patronReqModel) {

		Patron newPatron = new Patron();
		newPatron.setName(patronReqModel.getName());
		newPatron.setMobile(patronReqModel.getMobile());
		newPatron.setEmail(patronReqModel.getEmail());
		return newPatron;
	}

	public static void setDataToPatron(Patron patron, PatronsReqModel patronsReqModel) {

		patron.setName(patronsReqModel.getName());
		patron.setMobile(patronsReqModel.getMobile());
		patron.setEmail(patronsReqModel.getEmail());
	}

	public static BorrowingRecords mapToBorrowingRecords(BorrowReqModel borrowReqModel, Book book, Patron patron) {

		BorrowingRecords borrowingRecord = new BorrowingRecords();
		borrowingRecord.setBorrowDate(borrowReqModel.getBorrowDate());
		borrowingRecord.setBorrowedBook(book);
		borrowingRecord.setPatronData(patron);
		return borrowingRecord;
	}

	public static void validateRegisterRequest(RegisterReqModel request, int min, int max) {
		
		if (request.getFirstname() == null || request.getFirstname().isBlank() || request.getLastname() == null
				|| request.getLastname().isBlank()) {

			throw new BusinessLogicViolationException(
					ApiErrorEnum.FIRST_NAME_AND_LAST_NAME_MUST_BE_NOT_NULL_OR_BLANK.name());
		}

		if (request.getEmail() == null || request.getEmail().isBlank() || request.getPassword() == null
				|| request.getPassword().isBlank()) {

			throw new BusinessLogicViolationException(ApiErrorEnum.EMAIL_AND_PASSWORD_MUST_BE_NOT_NULL_OR_BLANK.name());
		}

		if (request.getPassword().length() < min || request.getPassword().length() > max) {

			throw new BusinessLogicViolationException(
					ApiErrorEnum.PASSWORD_LENGTH_MUST_BE_BETWEEN.name() + " " +  min + " And " + max);
		}
	}

	public static void validateAuthRequestBalnkOrNull(AuthenticationReqModel request) {
		
		if (request.getEmail() == null || request.getEmail().isBlank() || request.getPassword() == null
				|| request.getPassword().isBlank()) {

			throw new BusinessLogicViolationException(ApiErrorEnum.EMAIL_AND_PASSWORD_MUST_BE_NOT_NULL_OR_BLANK.name());
		}
	}

	public static void validateAuthRequestMatches(AuthenticationReqModel request, User user) {
		
		if (user != null) {
			if (!request.getEmail().equals(user.getEmail())) {

				throw new BusinessLogicViolationException(ApiErrorEnum.WRONG_EMAIL.name());
			}
		}
	}

	


}
