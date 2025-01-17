package com.example.library.test.record;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.library.entities.Book;
import com.example.library.entities.BorrowingRecords;
import com.example.library.entities.Patron;
import com.example.library.exceptions.BusinessLogicViolationException;
import com.example.library.models.BorrowReqModel;
import com.example.library.models.ReturnReqModel;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowingRecordRepository;
import com.example.library.repository.PatronRepository;
import com.example.library.serviceImpl.BookServiceImpl;
import com.example.library.serviceImpl.BorrowingRecordServiceImpl;
import com.example.library.serviceImpl.PatronServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordTest {

	@InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private PatronServiceImpl patronService;
    
    @Mock
    private PatronRepository patronRepository;
    
	@InjectMocks
	private BorrowingRecordServiceImpl BorrowingRecordService;

	@Mock
	private BorrowingRecordRepository borrowingRecordRepository;
	
	
	@Test
    void createBookBorrowingRecordTest() {
        
		Integer bookId = 1;
        Integer patronId = 2;

        Book book = new Book();
        book.setId(bookId);

        Patron patron = new Patron();
        patron.setId(patronId);

        BorrowReqModel borrowReqModel = new BorrowReqModel();

        BorrowingRecords borrowingRecord = new BorrowingRecords();
        borrowingRecord.setId(3); 

        // Mock the repository methods
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.save(any(BorrowingRecords.class))).thenReturn(borrowingRecord);

        // Call the service method
        Integer resultId = BorrowingRecordService.createBookBorrowigRecord(bookId, patronId, borrowReqModel);

        // Verify interactions
        verify(bookRepository).findById(bookId);
        verify(patronRepository).findById(patronId);
        verify(borrowingRecordRepository).save(any(BorrowingRecords.class));

        // Assert the result
        assertThat(resultId).isEqualTo(borrowingRecord.getId());
    }
		
	@Test
    void updateBorrowingRecordReturnDateTest() {
       
		Integer bookId = 1;
        Integer patronId = 2;

        Book book = new Book();
        book.setId(bookId);

        Patron patron = new Patron();
        patron.setId(patronId);

        BorrowingRecords oldRecord = new BorrowingRecords();
        oldRecord.setBorrowedBook(book);
        oldRecord.setPatronData(patron);
        oldRecord.setReturnDate(LocalDate.of(2024, 1, 1));

        ReturnReqModel returnReqModel = new ReturnReqModel();
        returnReqModel.setReturnDate(LocalDate.of(2024, 8, 1));

        // Mock repository methods
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId)).thenReturn(oldRecord);

        // Call the service method
        BorrowingRecordService.createBookReturnRecord(bookId, patronId, returnReqModel);

        // Verify interactions
        verify(bookRepository).findById(bookId);
        verify(patronRepository).findById(patronId);
        verify(borrowingRecordRepository).findByBookIdAndPatronId(bookId, patronId);
        verify(borrowingRecordRepository).save(oldRecord);

        // Assert the record is updated
        assertThat(oldRecord.getReturnDate()).isEqualTo(returnReqModel.getReturnDate());
    }

    @Test
    void updateBorrowingRecordReturnDateNotFoundTest() {
        Integer bookId = 1;
        Integer patronId = 2;

        ReturnReqModel returnReqModel = new ReturnReqModel();
        returnReqModel.setReturnDate(LocalDate.of(2024, 8, 1));

        // Mock repository methods
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
        when(borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId)).thenReturn(null);

        // Assert that a BusinessLogicViolationException is thrown
        assertThatThrownBy(() -> {
        	BorrowingRecordService.createBookReturnRecord(bookId, patronId, returnReqModel);
        }).isInstanceOf(BusinessLogicViolationException.class)
          .hasMessage("RECORD NOT FOUND");
    }
}
