package com.example.katacustomerjavaspringboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.domain.SloganRepository;
import com.example.katacustomerjavaspringboot.exceptions.MaxSlogansPerUserException;

@Service
public class SloganService {

	@Autowired
	SloganRepository repository;

	public Slogan create(final Slogan slogan) {
		final Long countByUserId = this.repository.countByUserId(slogan.getUserId());
		if (3 <= countByUserId) {
			throw new MaxSlogansPerUserException();
		}
		return this.repository.save(slogan);
	}

}
