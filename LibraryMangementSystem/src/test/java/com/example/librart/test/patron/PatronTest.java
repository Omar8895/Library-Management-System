package com.example.librart.test.patron;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

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

import com.example.library.entities.Patron;
import com.example.library.exceptions.BusinessLogicViolationException;
import com.example.library.models.PatronsReqModel;
import com.example.library.models.PatronsResModel;
import com.example.library.repository.PatronRepository;
import com.example.library.serviceImpl.PatronServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PatronTest {

	@InjectMocks
	private PatronServiceImpl patronService;

	@Mock
	private PatronRepository patronRepositorey;

	@Test
	void getAllPatronsTest() {
		int pageNo = 0;
		int pageSize = 5;
		Pageable pageable = PageRequest.of(pageNo, pageSize);

		Patron patron1 = new Patron();
		patron1.setId(1);
		patron1.setName("patron1");
		patron1.setEmail("patron1@gmail.com");
		patron1.setMobile("01111111111");

		Patron patron2 = new Patron();
		patron2.setId(1);
		patron2.setName("patron2");
		patron2.setEmail("patron2@gmail.com");
		patron2.setMobile("01111111112");

		List<Patron> patrons = Arrays.asList(patron1, patron2);
		Page<Patron> page = new PageImpl<>(patrons, pageable, patrons.size());

		// Mock repository methods
		when(patronRepositorey.findAll(pageable)).thenReturn(page);

		 // Call the service method
		List<PatronsResModel> result = patronService.getAllPatrons(pageNo, pageSize);

		// Assert the records is returned
		assertThat(result).hasSize(patrons.size());
		assertThat(result.get(0).getName()).isEqualTo(patron1.getName());
		assertThat(result.get(1).getName()).isEqualTo(patron2.getName());
	}
	
	@Test
	void createPatronTest() {

		PatronsReqModel patronReqModel = new PatronsReqModel();
		patronReqModel.setName("New Patron");
		patronReqModel.setEmail("patron@example.com");
		patronReqModel.setMobile("1234567890");

		Patron patron = new Patron();
		patron.setName(patronReqModel.getName());
		patron.setEmail(patronReqModel.getEmail());
		patron.setMobile(patronReqModel.getMobile());

		// Mock repository methods
		when(patronRepositorey.save(patron)).thenReturn(patron);

		// Call the service method
		Integer savedPatronId = patronService.createPatron(patronReqModel);
		
		// Verify interactions
		verify(patronRepositorey).save(patron);

		// Assert the record is created
		assertThat(savedPatronId).isEqualTo(patron.getId());
	}

	@Test
	void getPatronByIdTest() {

		int patronId = 1;

		// Mock repository methods
		when(patronRepositorey.findById(patronId)).thenReturn(Optional.empty());

		// Assert that a BusinessLogicViolationException is thrown
		assertThrows(BusinessLogicViolationException.class, () -> {
			patronService.getPatronById(patronId);
		});
	}

	@Test
	void updatePatronTest() {

		Integer patronId = 1;

		PatronsReqModel patronReqModel = new PatronsReqModel();
		patronReqModel.setName("Updated Patron Name");
		patronReqModel.setEmail("updated@example.com");
		patronReqModel.setMobile("0987654321");

		Patron existingPatron = new Patron();
		existingPatron.setId(patronId);
		existingPatron.setName("Old Name");
		existingPatron.setEmail("old@example.com");
		existingPatron.setMobile("1234567890");

		Patron updatedPatron = new Patron();
		updatedPatron.setId(patronId);
		updatedPatron.setName(patronReqModel.getName());
		updatedPatron.setEmail(patronReqModel.getEmail());
		updatedPatron.setMobile(patronReqModel.getMobile());

		PatronsResModel patronResModel = new PatronsResModel();
		patronResModel.setName(updatedPatron.getName());
		patronResModel.setEmail(updatedPatron.getEmail());
		patronResModel.setMobile(updatedPatron.getMobile());

		// Mock repository methods
		when(patronRepositorey.findById(patronId)).thenReturn(Optional.of(existingPatron));
		when(patronRepositorey.save(any(Patron.class))).thenReturn(updatedPatron);

		// Call the service method
		PatronsResModel result = patronService.updatePatron(patronId, patronReqModel);

		// Verify interactions
		verify(patronRepositorey).findById(patronId);
		verify(patronRepositorey).save(updatedPatron);
		  
		// Assert the record is updated
		assertThat(result).isEqualTo(patronResModel);
	}
	
	@Test
    void updatePatronNotFoundTest() {
        
		Integer patronId = 1;
		
        PatronsReqModel patronReqModel = new PatronsReqModel();
        patronReqModel.setName("Updated Patron Name");
        patronReqModel.setEmail("updated@example.com");
        patronReqModel.setMobile("0987654321");

        // Mock repository methods
        when(patronRepositorey.findById(patronId)).thenReturn(Optional.empty());

        // Assert the record is updated
        assertThrows(BusinessLogicViolationException.class, () -> {
            patronService.updatePatron(patronId, patronReqModel);
        });
    }
 
	@Test
    void deletePatronTest() {
        Integer patronId = 1;

        Patron patron = new Patron();
        patron.setId(patronId);

        // Mock repository methods
        when(patronRepositorey.findById(patronId)).thenReturn(Optional.of(patron));

        // Call the service method
        patronService.deletePatron(patronId);

        // Verify interactions
        verify(patronRepositorey).findById(patronId);
        verify(patronRepositorey).delete(patron);
    }

	@Test
    void deletePatronNotFoundTest() {
        Integer patronId = 1;

        // Mock repository methods
        when(patronRepositorey.findById(patronId)).thenReturn(Optional.empty());

        // Assert that a BusinessLogicViolationException is thrown
        assertThrows(BusinessLogicViolationException.class, () -> {
            patronService.deletePatron(patronId);
        });
    }
}
