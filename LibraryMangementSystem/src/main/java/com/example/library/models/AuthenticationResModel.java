package com.example.library.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -580509722043283973L;

	
	private String token;
}
