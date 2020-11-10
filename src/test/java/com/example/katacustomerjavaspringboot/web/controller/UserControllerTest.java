package com.example.katacustomerjavaspringboot.web.controller;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.services.UserService;

@SpringBootTest
public class UserControllerTest {

	@InjectMocks
	UserController controller;

	@Mock
	UserService service;

	@Test
	void givenUserWhenCreateThenShouldReturnOk() {
		// given
		final User user = User.builder().name("name").lastName("lastname").address("street").city("city")
				.email("sample@email.com").build();

		Mockito.when(this.service.create(user)).thenReturn(User.builder().id(UUID.randomUUID()).name("name")
				.lastName("lastname").address("street").city("city").email("sample@email.com").build());

		// when
		final ResponseEntity<User> responseEntity = this.controller.create(user);

		// then
		Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
		Assertions.assertNotNull(responseEntity.getHeaders());
		Assertions.assertTrue(responseEntity.getHeaders().get("Location").get(0).matches("^api/users/.*"));
	}

	@Test
	void givenUserIdWhenFindByIdThenShouldReturnUser() {
		// given
		final UUID uuid = UUID.randomUUID();

		Mockito.when(this.service.findById(uuid)).thenReturn(Optional.of(User.builder().id(uuid).name("name")
				.lastName("lastname").address("street").city("city").email("sample@email.com").build()));

		// when
		final ResponseEntity<User> responseEntity = this.controller.findById(uuid);

		// then
		Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
		Assertions.assertEquals(uuid, responseEntity.getBody().getId());
		Assertions.assertEquals("name", responseEntity.getBody().getName());
		Assertions.assertEquals("lastname", responseEntity.getBody().getLastName());
		Assertions.assertEquals("street", responseEntity.getBody().getAddress());
		Assertions.assertEquals("city", responseEntity.getBody().getCity());
		Assertions.assertEquals("sample@email.com", responseEntity.getBody().getEmail());
	}

	@Test
	void givenUserIdAndUserToUpdateWhenUpdateThenShouldReturnUserUpdated() {
		// given
		final UUID uuid = UUID.randomUUID();

		final User user = User.builder().id(uuid).name("nameUpdated").lastName("lastnameUpdated")
				.address("streetUpdated").city("cityUpdated").email("sampleUpdated@email.com").build();
		Mockito.when(this.service.update(uuid, user)).thenReturn(Optional.of(user));

		// when
		final ResponseEntity<User> responseEntity = this.controller.updateById(uuid, user);

		// then
		Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
		Assertions.assertEquals(uuid, responseEntity.getBody().getId());
		Assertions.assertEquals("nameUpdated", responseEntity.getBody().getName());
		Assertions.assertEquals("lastnameUpdated", responseEntity.getBody().getLastName());
		Assertions.assertEquals("streetUpdated", responseEntity.getBody().getAddress());
		Assertions.assertEquals("cityUpdated", responseEntity.getBody().getCity());
		Assertions.assertEquals("sampleUpdated@email.com", responseEntity.getBody().getEmail());
	}
}
