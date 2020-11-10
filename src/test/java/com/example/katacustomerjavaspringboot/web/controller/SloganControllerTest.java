package com.example.katacustomerjavaspringboot.web.controller;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.katacustomerjavaspringboot.domain.Slogan;
import com.example.katacustomerjavaspringboot.services.SloganService;

@SpringBootTest
class SloganControllerTest {
	@InjectMocks
	SloganController controller;

	@Mock
	SloganService service;

	@Test
	void givenSloganAndUserWithLowerThan3SlogansWhenCreateThenShouldAddSlogan() {
		// given
		final UUID uuid = UUID.randomUUID();
		final Slogan slogan = Slogan.builder().title("title").text("text").userId(uuid).build();

		final UUID sloganCreatedUUID = UUID.randomUUID();
		final Slogan sloganCreated = Slogan.builder().id(sloganCreatedUUID).title("title").text("text").userId(uuid)
				.build();
		Mockito.when(this.service.create(slogan)).thenReturn(sloganCreated);

		// when
		final ResponseEntity<Slogan> responseEntity = this.controller.create(slogan);

		// then
		Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
		Assertions.assertNotNull(responseEntity.getHeaders());
		Assertions.assertEquals("api/users/" + uuid + "/slogans/" + sloganCreatedUUID.toString(),
				responseEntity.getHeaders().getLocation().toString());

	}

}
