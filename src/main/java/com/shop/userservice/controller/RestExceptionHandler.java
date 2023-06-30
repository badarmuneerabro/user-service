package com.shop.userservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shop.userservice.dto.error.ErrorDetails;
import com.shop.userservice.dto.error.ValidationError;
import com.shop.userservice.exception.InvalidCredentialsException;
import com.shop.userservice.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) 
	{
		ErrorDetails details = new ErrorDetails();
		details.setTimestamp(new Date().getTime());
		details.setTitle("Message Not Readable");
		details.setStatus(status.value());
		details.setDeveloperMsg(ex.getClass().getName());
		details.setMessage(ex.getMessage());
		return handleExceptionInternal(ex, details, headers, status, request);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException exception)
	{
		ErrorDetails error = new ErrorDetails();
		error.setTitle("Resource Not Found.");
		error.setMessage(exception.getMessage());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(new Date().getTime());
		error.setDeveloperMsg(exception.getClass().getName());
		
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<?> invalidCredentialsExceptionHandler(InvalidCredentialsException exception)
	{
		ErrorDetails error = new ErrorDetails();
		
		error.setTitle("Invalid Credentials");
		error.setMessage(exception.getMessage());
		error.setDeveloperMsg(exception.getClass().getName());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(new Date().getTime());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) 
	{
ErrorDetails error = new ErrorDetails();
		
		error.setTitle("Field validation error.");
		error.setMessage("Input validation error.");
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setTimestamp(new Date().getTime());
		error.setDeveloperMsg(exception.getClass().getName());
		
		List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
		
		for(ObjectError e : allErrors)
		{
			List<ValidationError> list = error.getErrors().get(e.getObjectName());
			
			if(list == null)
			{
				list = new ArrayList<>();
				error.getErrors().put(e.getObjectName(), list);
				
			}
			
			ValidationError err = new ValidationError();
			err.setCode(e.getCode());
			err.setMessage(e.getDefaultMessage());
			
			list.add(err);
		}
		
		
		return new ResponseEntity<>(error, null, HttpStatus.BAD_REQUEST);
		
	}
}
