package com.example.katacustomerjavaspringboot.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.domain.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	public User create(final User user) {
		return this.repository.save(user);
	}

	public Optional<User> findById(final UUID uuid) {
		return this.repository.findById(uuid);
	}

}
