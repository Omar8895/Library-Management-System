package com.example.library.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.library.entities.Book;
import com.example.library.entities.Patron;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BorrowReqModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5245826290754506819L;

	@NotNull
	@ApiModelProperty(example = "YYYY-MM-DD")
	private LocalDate borrowDate;
	
	
	@ApiModelProperty(example = "YYYY-MM-DD")
	private LocalDate returnDate;
	
}
