package com.example.library.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BookReqModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2137232405053575260L;
	
	@NotNull
	@NotBlank
	@Size(max = 50)
	private String author;

	@NotBlank
	@Size(max = 50)
	private String title;

	@NotNull
	@ApiModelProperty(example = "YYYY-MM-DD")
	private  LocalDate publicationYear;
	
	@NotNull
	@NotBlank
	@Size(max = 50)
	private String isbn ;

}
