package com.example.katacustomerjavaspringboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.katacustomerjavaspringboot.converters.SloganDTOIntoSloganConverter;
import com.example.katacustomerjavaspringboot.converters.SloganIntoSloganDtoConverter;
import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.domain.SloganRepository;
import com.example.katacustomerjavaspringboot.exceptions.MaxSlogansPerUserException;
import com.example.katacustomerjavaspringboot.web.dto.SloganDTO;

@Service
public class SloganService {

	@Autowired
	SloganRepository repository;

	@Autowired
	SloganDTOIntoSloganConverter dtoConverter;

	@Autowired
	SloganIntoSloganDtoConverter entityConverter;

	public SloganDTO create(final SloganDTO dto) {
		final Long countByUserId = this.repository.countByUserId(dto.getUserId());
		if (3 <= countByUserId) {
			throw new MaxSlogansPerUserException();
		}

		final Slogan slogan = this.dtoConverter.convert(dto);
		final Slogan sloganSaved = this.repository.save(slogan);

		return this.entityConverter.convert(sloganSaved);
	}

}
