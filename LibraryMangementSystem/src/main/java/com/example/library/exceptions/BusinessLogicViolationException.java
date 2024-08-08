package com.example.library.exceptions;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BusinessLogicViolationException extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6354535564370790551L;
	
	private List<Map<String, String>> details;

	public BusinessLogicViolationException(String message) {
		super(message);
	}

	public BusinessLogicViolationException(String message, List<Map<String, String>> details) {
		super(message);
		this.setDetails(details);
	}

	public List<Map<String, String>> getDetails() {
		return details;
	}

	public void setDetails(List<Map<String, String>> details) {
		this.details = details;
	}

}
