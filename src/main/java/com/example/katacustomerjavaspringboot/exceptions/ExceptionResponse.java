package com.example.katacustomerjavaspringboot.exceptions;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
	private LocalDateTime timestamp;
	private String code;
	private String message;
}
