package com.zakaria.inventorymanagement.advice;

import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDto handleEntityNotFoundException(EntityNotFoundException exception) {
		return buildErrorDto(exception.getErrorCode(), HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
	
	@ExceptionHandler(InvalidOperationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDto handleInvalidOperationException(InvalidOperationException exception) {
		return buildErrorDto(exception.getErrorCode(), HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}
	
	@ExceptionHandler(InvalidEntityException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDto handleInvalidEntityException(InvalidEntityException exception) {
		return buildErrorDto(exception.getErrorCode(), HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getErrors());
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorDto handleBadCredentialsException(BadCredentialsException exception) {
		return buildErrorDto(ErrorCodes.BAD_CREDENTIALS, HttpStatus.UNAUTHORIZED.value(), "Username and / or password incorrect", Collections.singletonList(exception.getMessage()));
	}
	
	private ErrorDto buildErrorDto(ErrorCodes errorCode, int httpCode, String message) {
		return ErrorDto.builder()
				.code(errorCode)
				.httpCode(httpCode)
				.message(message)
				.build();
	}
	
	private ErrorDto buildErrorDto(ErrorCodes errorCode, int httpCode, String message, List<String> errors) {
		return ErrorDto.builder()
				.code(errorCode)
				.httpCode(httpCode)
				.message(message)
				.errors(errors)
				.build();
	}
}


