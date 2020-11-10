package com.example.katacustomerjavaspringboot.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.katacustomerjavaspringboot.domain.User;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService service;

	@Test
	void givenUserWhenCreateThenShouldReturnOk() {
		// given
		final User user = User.builder().name("name").lastName("lastname").address("street").city("city")
				.email("sample@email.com").build();

		// when
		final User userCreated = this.service.create(user);

		// then
		Assertions.assertNotNull(userCreated.getId());
		Assertions.assertEquals(user.getName(), userCreated.getName());
		Assertions.assertEquals(user.getLastName(), userCreated.getLastName());
		Assertions.assertEquals(user.getAddress(), userCreated.getAddress());
		Assertions.assertEquals(user.getCity(), userCreated.getCity());
		Assertions.assertEquals(user.getEmail(), userCreated.getEmail());
	}
}
