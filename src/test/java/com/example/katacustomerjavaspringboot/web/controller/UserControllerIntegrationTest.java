package com.example.katacustomerjavaspringboot.web.controller;

import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.example.katacustomerjavaspringboot.domain.User;

import io.restassured.http.ContentType;
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
				.given().standaloneSetup(this.controller).body(user).contentType(ContentType.JSON)

				// when
				.when().post("api/users")

				// then
				.then().log().all().statusCode(HttpStatus.CREATED.value());
	}

	@Test
	void givenInvalidUserIdRequestWhenFindByIdResourceThenShouldReturnUser() {
		final UUID uuid = UUID.randomUUID();

		final HashMap<String, String> params = new HashMap<String, String>();
		params.put("uuid", uuid.toString());

		RestAssuredMockMvc
				// given
				.given().standaloneSetup(this.controller)

				// when
				.when().get("api/users/{uuid}", params)

				// then
				.then().log().all().statusCode(HttpStatus.NOT_FOUND.value());
	}

}
