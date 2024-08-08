package com.example.library.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table( name="Book",uniqueConstraints = { @UniqueConstraint(name = "U_Isbn", columnNames = { "isbn"})})
public class Book implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 5605238167836088689L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false , length = 50)
	private String author;
	
	@Column(nullable = false , length = 50)
	private String title;
	
	@Column(nullable = false)
	private LocalDate publicationYear ;
	
	@Column(nullable = false , length = 50)
	private String isbn ;
	
	
	public Book (int id) {
		this.id = id;
	}
	

}
