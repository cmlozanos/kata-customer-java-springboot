package com.example.katacustomerjavaspringboot.web.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.services.SloganService;

@RestController
@RequestMapping("api/slogans")
public class SloganController {

	@Autowired
	SloganService service;

	@PostMapping
	public ResponseEntity<Slogan> create(@RequestBody final Slogan slogan) {
		final Slogan sloganCreated = this.service.create(slogan);
		final String url = "api/slogans/" + sloganCreated.getId();
		return ResponseEntity.created(URI.create(url)).build();
	}

}
