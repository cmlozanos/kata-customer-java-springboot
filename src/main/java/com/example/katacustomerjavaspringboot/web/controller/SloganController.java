package com.example.katacustomerjavaspringboot.web.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.katacustomerjavaspringboot.services.SloganService;
import com.example.katacustomerjavaspringboot.web.dto.SloganDTO;

@RestController
@RequestMapping("api/slogans")
public class SloganController {

	@Autowired
	SloganService service;

	@PostMapping
	public ResponseEntity<SloganDTO> create(@RequestBody final SloganDTO slogan) {
		final SloganDTO sloganCreated = this.service.create(slogan);
		final String url = "api/slogans/" + sloganCreated.getId();
		return ResponseEntity.created(URI.create(url)).build();
	}

}
