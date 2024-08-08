package com.example.library.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiBadRequestError {
	
	private String id;

	private LocalDateTime timestamp;

	private String code;

	private List<Error> errors;
	
	private int httpStatus;

}
