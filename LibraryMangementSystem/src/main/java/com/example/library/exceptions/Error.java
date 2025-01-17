package com.example.library.exceptions;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {
	
	private String field;

	private String messageKey;

	private String params;
	
	private int httpStatus;

}
