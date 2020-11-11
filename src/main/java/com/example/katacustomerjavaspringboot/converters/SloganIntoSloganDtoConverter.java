package com.example.katacustomerjavaspringboot.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.web.dto.SloganDTO;

@Component
public class SloganIntoSloganDtoConverter implements Converter<Slogan, SloganDTO> {

	@Override
	public SloganDTO convert(final Slogan source) {
		// TODO Auto-generated method stub
		return null;
	}

}
