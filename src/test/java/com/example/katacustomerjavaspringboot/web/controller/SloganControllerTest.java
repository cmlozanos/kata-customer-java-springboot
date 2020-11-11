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

import com.example.katacustomerjavaspringboot.services.SloganService;
import com.example.katacustomerjavaspringboot.web.dto.SloganDTO;

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
		final SloganDTO slogan = SloganDTO.builder().title("title").text("text").userId(uuid).build();

		final UUID sloganCreatedUUID = UUID.randomUUID();
		final SloganDTO sloganCreated = SloganDTO.builder().id(sloganCreatedUUID).title("title").text("text")
				.userId(uuid).build();
		Mockito.when(this.service.create(slogan)).thenReturn(sloganCreated);

		// when
		final ResponseEntity<SloganDTO> responseEntity = this.controller.create(slogan);

		// then
		Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
		Assertions.assertNotNull(responseEntity.getHeaders());
		Assertions.assertEquals("api/slogans/" + sloganCreatedUUID.toString(),
				responseEntity.getHeaders().getLocation().toString());

	}

}
