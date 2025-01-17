package com.example.library.entities;

import java.io.Serializable;

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
@Table(name="Patron" ,uniqueConstraints = { @UniqueConstraint(name = "U_Mobile", columnNames = { "mobile"}) ,
							  @UniqueConstraint(name = "U_Email", columnNames = { "email" })})
public class Patron implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4440073156665842949L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@Column(nullable = false , length = 50)
	private String name ;
	
	@Column(nullable = false , length = 12)
	private String mobile;
	
	@Column(nullable = false , length = 50)
	private String email;
	
	public Patron (int id) {
		this.id = id;
	}

}
