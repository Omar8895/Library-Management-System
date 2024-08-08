package com.example.library.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReturnReqModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4086255199336040014L;
	
	@NotNull
	@ApiModelProperty(example = "YYYY-MM-DD")
	private LocalDate returnDate;

}
