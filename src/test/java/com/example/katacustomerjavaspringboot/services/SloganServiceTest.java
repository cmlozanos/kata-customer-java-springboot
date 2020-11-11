package com.example.katacustomerjavaspringboot.services;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.katacustomerjavaspringboot.converters.SloganDTOIntoSloganConverter;
import com.example.katacustomerjavaspringboot.converters.SloganIntoSloganDtoConverter;
import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.domain.SloganRepository;
import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.exceptions.MaxSlogansPerUserException;
import com.example.katacustomerjavaspringboot.web.dto.SloganDTO;

@SpringBootTest
class SloganServiceTest {

	@InjectMocks
	SloganService service;

	@Mock
	SloganRepository repository;

	@Mock
	SloganDTOIntoSloganConverter dtoConverter;

	@Mock
	SloganIntoSloganDtoConverter entityConverter;

	@Test
	void givenSloganAndUserWithLowerThan3SlogansWhenCreateThenShouldAddSlogan() {
		// given
		final UUID uuid = UUID.randomUUID();
		final SloganDTO sloganDTO = SloganDTO.builder().title("title").text("text").userId(uuid).build();

		Mockito.when(this.repository.countByUserId(uuid)).thenReturn(0L);

		final Slogan slogan = Slogan.builder().title("title").text("text").user(User.builder().id(uuid).build())
				.build();
		Mockito.when(this.dtoConverter.convert(sloganDTO)).thenReturn(slogan);

		final UUID sloganCreatedUUID = UUID.randomUUID();
		final Slogan sloganSaved = Slogan.builder().id(sloganCreatedUUID).title("title").text("text")
				.user(User.builder().id(uuid).build()).build();
		Mockito.when(this.repository.save(slogan)).thenReturn(sloganSaved);

		final SloganDTO sloganDTOSaved = SloganDTO.builder().id(sloganCreatedUUID).title("title").text("text")
				.userId(uuid).build();
		Mockito.when(this.entityConverter.convert(sloganSaved)).thenReturn(sloganDTOSaved);

		// when
		final SloganDTO sloganCreated = this.service.create(sloganDTO);

		// then
		Assertions.assertEquals(sloganCreatedUUID, sloganCreated.getId());
		Assertions.assertEquals(sloganDTO.getTitle(), sloganCreated.getTitle());
		Assertions.assertEquals(sloganDTO.getText(), sloganCreated.getText());
		Assertions.assertEquals(sloganDTO.getUserId(), sloganCreated.getUserId());

	}

	@Test
	void givenSloganAndUserWith3SlogansWhenCreateThenShouldAddSlogan() {
		// given
		final UUID uuid = UUID.randomUUID();
		final SloganDTO slogan = SloganDTO.builder().title("title").text("text").userId(uuid).build();

		Mockito.when(this.repository.countByUserId(uuid)).thenReturn(3L);

		// when - then
		Assertions.assertThrows(MaxSlogansPerUserException.class, () -> this.service.create(slogan));

	}

}
