package com.example.katacustomerjavaspringboot.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.example.katacustomerjavaspringboot.domain.User;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

	@Autowired
	UserController controller;

	@Test
	void givenPostRequestWhenAddResourceThenShouldAddUser() {
		final User user = User.builder().name("name").lastName("lastname").address("street").city("city")
				.email("sample@email.com").build();

		RestAssuredMockMvc
				// given
				.given().standaloneSetup(this.controller).body(user)

				// when
				.when().post("api/users")

				// then
				.then().log().all().statusCode(HttpStatus.CREATED.value());
	}

}
