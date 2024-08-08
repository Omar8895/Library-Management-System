package com.example.library.models;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PatronsReqModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5840161471396147626L;
	
	@NotNull
	@NotBlank
	@Size(max = 50)
	private String name;

	@NotNull
	@NotBlank
	@Size(max = 50)
	private String mobile;

	@NotNull
	@NotBlank
	@Size(max = 50)
	private String email ;

}
