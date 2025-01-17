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

public class AuthenticationReqModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7368380047153327264L;

	@NotBlank
	@NotNull
	private String email;
	
	@NotBlank
	@NotNull
	private String Password;
}
