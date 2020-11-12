package com.example.katacustomerjavaspringboot.exceptions;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	static final String CODE = "002";
	static final String MESSAGE = "User not found";
}
