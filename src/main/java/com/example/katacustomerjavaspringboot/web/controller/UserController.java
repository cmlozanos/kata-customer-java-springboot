package com.example.katacustomerjavaspringboot.web.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.services.UserService;
import com.sun.istack.NotNull;

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	public UserService service;

	@PostMapping
	public ResponseEntity<User> create(@NotNull @RequestBody final User user) {
		final User userCreated = this.service.create(user);
		return ResponseEntity.created(URI.create("api/users/" + userCreated.getId())).build();
	}

	@GetMapping("{uuid}")
	public ResponseEntity<User> findById(@NotNull @PathVariable final UUID uuid) {
		return ResponseEntity.of(this.service.findById(uuid));
	}

}
