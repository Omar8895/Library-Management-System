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
import com.example.library.models.PatronsReqModel;
import com.example.library.models.PatronsResModel;
import com.example.library.service.PatronService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path="/api/patrons")
public class PatronController {
	
	@Autowired
	private PatronService patronService;
	
	@ApiOperation("Get All Patrons")
	@GetMapping
	public ResponseEntity<List<PatronsResModel>> getAllPatrons(
			@RequestParam(value = "pageNo" ,defaultValue = "0" , required = false) int pageNo,
			@RequestParam(value = "pageSize" ,defaultValue = "5" , required = false) int pageSize
			) {
		return new ResponseEntity<>(patronService.getAllPatrons(pageNo,pageSize), HttpStatus.OK);
	}
	
	@ApiOperation("Create New Patron")
	@PostMapping
	public ResponseEntity<Integer> createPatron(
			@ApiParam("Patron Req Model") @Valid @RequestBody PatronsReqModel patronReqModel) {
		return new ResponseEntity<>(patronService.createPatron(patronReqModel), HttpStatus.OK);
	}
	
	
	@ApiOperation("Get Patron By Id")
	@GetMapping(path = "/{id}")
	public ResponseEntity<PatronsResModel> getPatronById(
			@ApiParam("Patron Id") @PathVariable("id") Integer patronId) {
		return new ResponseEntity<>(patronService.getPatronById(patronId), HttpStatus.OK);
	}
	
	@ApiOperation("Update Patron")
	@PutMapping(path = "/{id}")
	public ResponseEntity<PatronsResModel> updatePatron(@ApiParam("patron Id") @PathVariable("id") Integer patronId,
			@ApiParam("Patron Request Model") @Valid @RequestBody PatronsReqModel patronsReqModel) {
		return new ResponseEntity<>(patronService.updatePatron(patronId, patronsReqModel),
				HttpStatus.OK);
	}
	
	@ApiOperation("Delete Patron")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deletePatron(@ApiParam("Patron Id") @PathVariable("id") Integer patronId) {	
		patronService.deletePatron(patronId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
