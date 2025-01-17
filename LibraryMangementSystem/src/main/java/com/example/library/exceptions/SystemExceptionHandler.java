package com.example.library.exceptions;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import javax.validation.ConstraintViolationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.library.enums.ApiErrorEnum;

@ControllerAdvice
public class SystemExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(SystemExceptionHandler.class);
	
	@ExceptionHandler({ BusinessLogicViolationException.class })
	public ResponseEntity<Object> handleBussinessLogicException(BusinessLogicViolationException ex) {
		int status = HttpStatus.BAD_REQUEST.value();
		ApiBusinessError apiBusinessError = new ApiBusinessError(UUID.randomUUID().toString(), LocalDateTime.now(),
				ApiErrorEnum.BUSINESS_CONSTRAINT_VIOLATION.name(), ex.getMessage(), ex.getDetails(), status);
		logger.info("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(apiBusinessError, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> runTimeExceptionHandler(RuntimeException ex) {
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		ex.printStackTrace();
		ApiError apiError = new ApiError(UUID.randomUUID().toString(), LocalDateTime.now(),
				ApiErrorEnum.INTERNAL_SERVER_ERROR.name(),ApiErrorEnum.RUN_TIME_EXCEPTION.name(),status);
		logger.info("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	@ExceptionHandler({ NullPointerException.class })
	public ResponseEntity<Object> exceptionHandler(NullPointerException ex) {
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		ex.printStackTrace();
		ApiError apiError = new ApiError(UUID.randomUUID().toString(), LocalDateTime.now(),
				ApiErrorEnum.INTERNAL_SERVER_ERROR.name(),ApiErrorEnum.RUN_TIME_EXCEPTION.name(),status);
		logger.info("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleJavaxValidationException(ConstraintViolationException ex) {
		
		int status = HttpStatus.BAD_REQUEST.value();
		List<Error> errors = new ArrayList<Error>();

		List<String> errorList = Arrays.asList(ex.getMessage().split("\\s*,\\s*"));

		for (String error : errorList) {
			String capitalizedMessage = capitalizeMessage(getValidationMessage(error));
			errors.add(getValidationErrorMessage(capitalizedMessage, getValidationField(error)));
		}

		ApiBadRequestError apiBadRequestError = new ApiBadRequestError(UUID.randomUUID().toString(),
				LocalDateTime.now(),ApiErrorEnum.BAD_REQUEST.name(), errors , status);
		logger.info("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(apiBadRequestError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		
		int status = HttpStatus.BAD_REQUEST.value();
		List<Error> errors = new ArrayList<Error>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String capitalizedMessage = capitalizeMessage(error.getDefaultMessage());
			errors.add(getValidationErrorMessage(capitalizedMessage, ((FieldError) error).getField()));
		}

		ApiBadRequestError apiBadRequestError = new ApiBadRequestError(UUID.randomUUID().toString(),
				LocalDateTime.now(),ApiErrorEnum.BAD_REQUEST.name(), errors , status );
		logger.info("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(apiBadRequestError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
		
		int status = HttpStatus.FORBIDDEN.value();
		ApiError apiError = new ApiError(UUID.randomUUID().toString(), LocalDateTime.now(),
				ApiErrorEnum.ACCESS_IS_DENIED.name(), ApiErrorEnum.ACCESS_IS_DENIED.name(),status);
		logger.info("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> generalError(Exception ex) {
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		ApiError apiError = new ApiError(UUID.randomUUID().toString(), LocalDateTime.now(),
				ApiErrorEnum.INTERNAL_SERVER_ERROR.name(),ApiErrorEnum.GENERAL_ERROR.name(),status);
		logger.info("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handlePersistenceException(DataIntegrityViolationException ex) {

		int status = HttpStatus.BAD_REQUEST.value();
		ApiError apiError = null;
		Throwable cause = ex.getRootCause();

		if (cause instanceof SQLIntegrityConstraintViolationException
				? ((SQLIntegrityConstraintViolationException) cause != null)
				: false || cause instanceof SQLException ? ((SQLException) cause) != null : false) {
			apiError = SQLIntegrityConstraintViolation(cause.toString());
		} else if (cause instanceof ConstraintViolationException) {
			apiError = new ApiError(UUID.randomUUID().toString(), LocalDateTime.now(),
					ApiErrorEnum.INTERNAL_SERVER_ERROR.name(),
					ApiErrorEnum.DATABASE_CONSTRAINT_VIOLATION.name(),status);
		}
		logger.info("xxxxxxxxx   Exception : : : " + ex.getMessage());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	private ApiError SQLIntegrityConstraintViolation(String exceptionMessage) {
		String message = null;
		String code = null;
		int status = HttpStatus.BAD_REQUEST.value();
		if (exceptionMessage.contains("ORA-00001")) {// UK
			message = getConstraintKeyFromExceptionMessage("U_", exceptionMessage);
			code = ApiErrorEnum.UNIQUE_KEY_CONSTRAINT_VIOLATION.name();
		} else if (exceptionMessage.contains("ORA-01400")) {
			message = exceptionMessage.substring(exceptionMessage.indexOf("ORA-01400:") + ("ORA-01400:").length());
			code = ApiErrorEnum.INTERNAL_SERVER_ERROR.name();
		} else if (exceptionMessage.contains("ORA-01407")) {// Not Null
			message = exceptionMessage.substring(exceptionMessage.indexOf("ORA-01407:") + ("ORA-01407:").length());
			code = ApiErrorEnum.INTERNAL_SERVER_ERROR.name();
		} else if (exceptionMessage.contains("ORA-02291")) {// Create And FK Not Found
			message = getConstraintKeyFromExceptionMessage("FK_", exceptionMessage);
			code = ApiErrorEnum.FOREIGN_KEY_CONSTRAINT_VIOLATION.name();
		} else if (exceptionMessage.contains("ORA-02292")) {// Delete And Child Record Found
			message = getConstraintKeyFromExceptionMessage("FK_", exceptionMessage);
			code = ApiErrorEnum.FOREIGN_KEY_CONSTRAINT_VIOLATION.name();
		}
		return new ApiError(UUID.randomUUID().toString(), LocalDateTime.now(), code, message , status);
	}

	private String getConstraintKeyFromExceptionMessage(String prefix, String exceptionMessage) {
		Matcher matcher = Pattern.compile(prefix + "(\\w+)").matcher(exceptionMessage);
		return matcher.find() ? matcher.group() : "";
	}


	
	private String getValidationField(String error) {
		Pattern pattern = Pattern.compile("\\.(.*):\\s");
		Matcher matcher = pattern.matcher(error);
		if (matcher.find())
			return matcher.group(1);
		return "";
	}

	private String getValidationMessage(String error) {
		Pattern pattern = Pattern.compile(":\\s(.*)");
		Matcher matcher = pattern.matcher(error);
		if (matcher.find())
			return matcher.group(1);
		return "";
	}
	
	private String capitalizeMessage(String message) {
		return message.toUpperCase().replaceAll(" ", "_");
	}

	private Error getValidationErrorMessage(String capitalizedMessage, String field) {
		
		int status = HttpStatus.BAD_REQUEST.value();
		String params = "";

		if (capitalizedMessage.contains("OR_EQUAL_TO_")) { 
			params = capitalizedMessage
					.substring(capitalizedMessage.indexOf("OR_EQUAL_TO_") + ("OR_EQUAL_TO_").length());
			capitalizedMessage = capitalizedMessage.substring(0, capitalizedMessage.indexOf(params)).concat("VALUE");
		} else if (capitalizedMessage.contains("SIZE_MUST_BE_BETWEEN_")) { 
			params = capitalizedMessage.substring(
					capitalizedMessage.indexOf("SIZE_MUST_BE_BETWEEN_") + ("SIZE_MUST_BE_BETWEEN_").length());
			capitalizedMessage = capitalizedMessage.substring(0, capitalizedMessage.indexOf(params)).concat("VALUES");
			params = params.replace("AND", ",").replace("_", "");
		} else if (capitalizedMessage.contains("MUST_MATCH")) { 
			params = capitalizedMessage.substring(capitalizedMessage.indexOf("MUST_MATCH_") + ("MUST_MATCH_").length());
			capitalizedMessage = capitalizedMessage.substring(0, capitalizedMessage.indexOf(params) - 1);
		}

		return new Error(field, capitalizedMessage, params , status);

	}


}
