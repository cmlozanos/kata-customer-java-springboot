package com.example.katacustomerjavaspringboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.services.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	public UserService service;

	public User create(final User user) {
		return this.service.create(user);
	}

}
