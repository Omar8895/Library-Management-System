package com.example.library.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.library.entities.Patron;
import com.example.library.exceptions.BusinessLogicViolationException;
import com.example.library.helper.Helper;
import com.example.library.models.PatronsReqModel;
import com.example.library.models.PatronsResModel;
import com.example.library.repository.PatronRepository;
import com.example.library.service.PatronService;

import org.springframework.data.domain.Pageable;

@Service
public class PatronServiceImpl implements PatronService{
	
	@Autowired
	private PatronRepository patronRepositorey;

	@Cacheable("patrons")
	@Override
	public List<PatronsResModel> getAllPatrons(int pageNo , int pageSize) {
		
		Pageable page = PageRequest.of(pageNo, pageSize);
		Page<Patron>allPatrons = patronRepositorey.findAll(page);
		List<Patron> listOfPatrons = allPatrons.getContent();
		return listOfPatrons.stream().map(p -> Helper.mapToPatronsResModel(p)).collect(Collectors.toList());
		
	}
	
	

	@Override
	@Transactional
	public Integer createPatron(PatronsReqModel patronReqModel) {
		
		return patronRepositorey.save(Helper.mapToPatronsReqModel(patronReqModel)).getId();
	}
	
	

	@Override
	public PatronsResModel getPatronById(Integer id) {		
		
		Patron patron = patronRepositorey.findById(id).orElseThrow(()->  new BusinessLogicViolationException("PATRON NOT FOUND"));
		return Helper.mapToPatronsResModel(patron);	
	}
	
	
	@Override
	@Transactional
	public PatronsResModel updatePatron(Integer id, PatronsReqModel patronsReqModel) {
		
		Patron patron = patronRepositorey.findById(id).orElseThrow(()->  new BusinessLogicViolationException("PATRON NOT FOUND"));
		Helper.setDataToPatron(patron,patronsReqModel);
		patronRepositorey.save(patron);
		return Helper.mapToPatronsResModel(patron);
	}

	@Override
	public void deletePatron(Integer id) {
		
		patronRepositorey.delete(patronRepositorey.findById(id).orElseThrow(()-> new BusinessLogicViolationException("PATRON NOT FOUND")));
	}

}
