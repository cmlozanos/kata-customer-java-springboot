package com.example.katacustomerjavaspringboot.services;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.domain.SloganRepository;
import com.example.katacustomerjavaspringboot.exceptions.MaxSlogansPerUserException;

@SpringBootTest
class SloganServiceTest {

	@InjectMocks
	SloganService service;

	@Mock
	SloganRepository repository;

	@Test
	void givenSloganAndUserWithLowerThan3SlogansWhenCreateThenShouldAddSlogan() {
		// given
		final UUID uuid = UUID.randomUUID();
		final Slogan slogan = Slogan.builder().title("title").text("text").userId(uuid).build();

		Mockito.when(this.repository.countByUserId(uuid)).thenReturn(0L);

		final UUID sloganCreatedUUID = UUID.randomUUID();
		Mockito.when(this.repository.save(slogan))
				.thenReturn(Slogan.builder().id(sloganCreatedUUID).title("title").text("text").userId(uuid).build());

		// when
		final Slogan sloganCreated = this.service.create(slogan);

		// then
		Assertions.assertEquals(sloganCreatedUUID, sloganCreated.getId());
		Assertions.assertEquals(slogan.getTitle(), sloganCreated.getTitle());
		Assertions.assertEquals(slogan.getText(), sloganCreated.getText());
		Assertions.assertEquals(slogan.getUserId(), sloganCreated.getUserId());

	}

	@Test
	void givenSloganAndUserWith3SlogansWhenCreateThenShouldAddSlogan() {
		// given
		final UUID uuid = UUID.randomUUID();
		final Slogan slogan = Slogan.builder().title("title").text("text").userId(uuid).build();

		Mockito.when(this.repository.countByUserId(uuid)).thenReturn(3L);

		// when - then
		Assertions.assertThrows(MaxSlogansPerUserException.class, () -> this.service.create(slogan));

	}

}
