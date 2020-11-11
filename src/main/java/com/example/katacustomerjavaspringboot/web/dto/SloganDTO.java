package com.example.katacustomerjavaspringboot.web.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SloganDTO {
	
	private UUID id;
	private String title;
	private String text;
    private UUID userId;
}
