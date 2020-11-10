package com.example.katacustomerjavaspringboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.domain.SloganRepository;

@Service
public class SloganService {

	@Autowired
	SloganRepository repository;

	public Slogan create(final Slogan slogan) {
		return this.repository.save(slogan);
	}

}
