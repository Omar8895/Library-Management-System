package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.library.models.AuthenticationReqModel;
import com.example.library.models.AuthenticationResModel;
import com.example.library.models.RegisterReqModel;
import com.example.library.service.AuthenticationService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
	private final AuthenticationService authenticationService;
	
	@ApiOperation("Add New User")
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResModel> register(
			@RequestBody RegisterReqModel request){
		
		return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);
	}
	
	
	@ApiOperation("Authenticate User")
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResModel> authenticate(
			@RequestBody AuthenticationReqModel request){
		
		return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
	}

}
