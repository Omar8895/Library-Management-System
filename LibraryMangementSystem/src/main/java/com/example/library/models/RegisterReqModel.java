package com.example.library.models;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterReqModel implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 675999344987488348L;

	@NotNull
	@NotBlank
	private String firstname;
	
	@NotNull
	@NotBlank
	private String lastname;
	
	@NotNull
	@NotBlank
	private String email;
	
	@NotNull
	@NotBlank
	private String password;
}
