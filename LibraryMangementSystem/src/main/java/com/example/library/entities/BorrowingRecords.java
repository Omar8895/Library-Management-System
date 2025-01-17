package com.example.library.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table( name="borrowingrecords",uniqueConstraints = { @UniqueConstraint(name = "U_book_patron_dates",
								columnNames = { "borrowDate","returnDate","Book_Id","Patron_Id"})})
public class BorrowingRecords implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8136349888349751183L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private LocalDate borrowDate;

	@Column(nullable = true)
	private LocalDate returnDate;
	
	@ManyToOne
	@JoinColumn(name = "Book_Id", nullable = false, foreignKey = @ForeignKey(name = "Book_FK"))
	private Book borrowedBook;
	
	@ManyToOne
	@JoinColumn(name = "Patron_Id", nullable = false, foreignKey = @ForeignKey(name = "Patron_FK"))
	private Patron patronData;
	
	

}
