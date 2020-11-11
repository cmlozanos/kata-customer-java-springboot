package com.example.katacustomerjavaspringboot.converters;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.web.dto.SloganDTO;

public class SloganDTOIntoSloganConverterTest {

	@Autowired
	SloganDTOIntoSloganConverter converter;

	@Test
	void givenSloganDTOWhenConvertIntoSloganShouldAssignProperly() {
		// given
		final UUID uuid = UUID.randomUUID();
		final UUID userId = UUID.randomUUID();
		final SloganDTO dto = SloganDTO.builder().id(uuid).title("title").text("text").userId(userId).build();

		// when
		final Slogan slogan = this.converter.convert(dto);

		// then
		Assertions.assertEquals(uuid, slogan.getId());
		Assertions.assertEquals("title", slogan.getTitle());
		Assertions.assertEquals("text", slogan.getText());
		Assertions.assertEquals(userId, slogan.getUser().getId());
	}
}
