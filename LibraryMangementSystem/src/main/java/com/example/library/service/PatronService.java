package com.example.library.service;

import java.util.List;

import com.example.library.models.PatronsReqModel;
import com.example.library.models.PatronsResModel;

public interface PatronService {
	
	List<PatronsResModel> getAllPatrons(int pageNo , int pageSize);
	
	Integer createPatron(PatronsReqModel patronReqModel);
	
	PatronsResModel getPatronById(Integer id);
	
	PatronsResModel updatePatron (Integer id , PatronsReqModel patronsReqModel);
	
	void deletePatron(Integer id);

}
