package com.example.katacustomerjavaspringboot.web.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.katacustomerjavaspringboot.domain.User;

@SpringBootTest
public class UserControllerTest {

	@Autowired
	UserController controller;

	@Test
	void givenUserWhenCreateThenShouldReturnOk() {
		// given
		final User user = User.builder().name("name").lastName("lastname").address("street").city("city")
				.email("sample@email.com").build();

		// when
		final ResponseEntity<User> responseEntity = this.controller.create(user);

		// then
		Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
		Assertions.assertNotNull(responseEntity.getBody());
		Assertions.assertNotNull(responseEntity.getBody().getId());
		Assertions.assertEquals(user.getName(), responseEntity.getBody().getName());
		Assertions.assertEquals(user.getLastName(), responseEntity.getBody().getLastName());
		Assertions.assertEquals(user.getAddress(), responseEntity.getBody().getAddress());
		Assertions.assertEquals(user.getCity(), responseEntity.getBody().getCity());
		Assertions.assertEquals(user.getEmail(), responseEntity.getBody().getEmail());
	}
}
