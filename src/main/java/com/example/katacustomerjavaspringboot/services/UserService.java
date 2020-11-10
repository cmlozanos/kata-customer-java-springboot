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

	public Optional<User> update(final UUID uuid, final User user) {
		final Optional<User> findById = this.repository.findById(uuid);
		if (!findById.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(this.repository.save(user));
	}

}
