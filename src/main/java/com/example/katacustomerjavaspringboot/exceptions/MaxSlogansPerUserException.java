package com.example.katacustomerjavaspringboot.exceptions;

public class MaxSlogansPerUserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	static final String CODE = "001";
	static final String MESSAGE = "Max slogans per user exceed. Only allow 3 per user";
}
