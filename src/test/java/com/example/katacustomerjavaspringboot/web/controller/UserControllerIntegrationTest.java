package com.example.katacustomerjavaspringboot.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

	@Autowired
	UserController controller;

	@Test
	void givenPostRequestWhenAddResourceThenShouldAddUser() {
		RestAssuredMockMvc
				// given
				.given().standaloneSetup(this.controller)

				// when
				.when().post("api/users")

				// then
				.then().statusCode(HttpStatus.CREATED.value());
	}

}
