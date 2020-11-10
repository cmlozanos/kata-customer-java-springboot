package com.example.katacustomerjavaspringboot.web.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.services.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	public UserService service;

	@PostMapping
	public ResponseEntity<User> create(final User user) {
		final User userCreated = this.service.create(user);
		return ResponseEntity.created(URI.create("api/users/" + userCreated.getId())).build();
	}

}
