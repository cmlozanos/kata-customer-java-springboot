package com.example.katacustomerjavaspringboot.converters;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.web.dto.SloganDTO;

@SpringBootTest
public class SloganIntoSloganDtoConverterTest {

	@Autowired
	SloganIntoSloganDtoConverter converter;

	@Test
	void givenSloganWhenConvertIntoSloganDTOShouldAssignProperly() {
		final UUID uuid = UUID.randomUUID();
		final UUID userId = UUID.randomUUID();
		final Slogan entity = Slogan.builder().id(uuid).title("title").text("text")
				.user(User.builder().id(userId).build()).build();

		// when
		final SloganDTO dto = this.converter.convert(entity);

		// then
		Assertions.assertEquals(uuid, dto.getId());
		Assertions.assertEquals("title", dto.getTitle());
		Assertions.assertEquals("text", dto.getText());
		Assertions.assertEquals(userId, dto.getUserId());
	}

}
