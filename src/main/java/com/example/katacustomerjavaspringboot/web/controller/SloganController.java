package com.example.katacustomerjavaspringboot.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.katacustomerjavaspringboot.domain.Slogan;

@RestController
@RequestMapping("api/slogans")
public class SloganController {

	public ResponseEntity<Slogan> create(final Slogan slogan) {
		// TODO Auto-generated method stub
		return null;
	}

}
