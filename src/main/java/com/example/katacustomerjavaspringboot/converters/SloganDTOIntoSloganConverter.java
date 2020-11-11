package com.example.katacustomerjavaspringboot.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.web.dto.SloganDTO;

@Component
public class SloganDTOIntoSloganConverter implements Converter<SloganDTO, Slogan> {

	@Override
	public Slogan convert(final SloganDTO source) {
		return Slogan.builder().id(source.getId()).title(source.getTitle()).text(source.getText())
				.user(User.builder().id(source.getUserId()).build()).build();
	}

}
