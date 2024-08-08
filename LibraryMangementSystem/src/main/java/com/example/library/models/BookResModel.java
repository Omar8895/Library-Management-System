package com.example.library.models;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BookResModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8864943266295471435L;
	
	private String author;
	private String title;
	private  LocalDate publicationYear;
	private String isbn ;
	
}
