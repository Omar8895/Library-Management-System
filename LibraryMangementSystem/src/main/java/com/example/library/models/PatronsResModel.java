package com.example.library.models;

import java.io.Serializable;



import lombok.Data;

@Data
public class PatronsResModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6544420056329244614L;
	
	private String name ;
	private String mobile;
	private String email;

}
