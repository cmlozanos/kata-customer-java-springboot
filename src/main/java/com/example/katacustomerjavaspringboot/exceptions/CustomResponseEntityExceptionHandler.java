package com.example.katacustomerjavaspringboot.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MaxSlogansPerUserException.class)
	public ResponseEntity<Object> maxSlogansPerUserException() {
		return ResponseEntity.badRequest().body(ExceptionResponse.builder().timestamp(LocalDateTime.now())
				.code(MaxSlogansPerUserException.CODE).message(MaxSlogansPerUserException.MESSAGE).build());
	}

}
