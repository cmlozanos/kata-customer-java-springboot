package com.example.katacustomerjavaspringboot.web.controller;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.example.katacustomerjavaspringboot.domain.Slogan;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SloganControllerIntegrationTest {

	@Autowired
	SloganController controller;

	@Test
	void givenUserIdAndSloganAndLowerThan3SlogansWhenAddResourceThenShouldAddVerifyAmountAndAddSlogan() {
		final UUID uuid = UUID.randomUUID();
		final Slogan slogan = Slogan.builder().title("title").text("text").userId(uuid).build();

		RestAssuredMockMvc
				// given
				.given().standaloneSetup(this.controller).body(slogan).contentType(ContentType.JSON)

				// when
				.when().post("api/slogans")

				// then
				.then().log().all().statusCode(HttpStatus.CREATED.value());
	}

	@Test
	void givenUserIdAndSloganAnd3SlogansWhenAddResourceThenShouldAddVerifyAmountAndReturnKOResponse() {
		final UUID uuid = UUID.randomUUID();
		final Slogan slogan = Slogan.builder().title("title").text("text").userId(uuid).build();

		// Add first slogan
		RestAssuredMockMvc.given().standaloneSetup(this.controller)
				.body(Slogan.builder().title("title1").text("text").userId(uuid).build()).contentType(ContentType.JSON)
				.post("api/slogans");

		// Add second slogan
		RestAssuredMockMvc.given().standaloneSetup(this.controller)
				.body(Slogan.builder().title("title2").text("text").userId(uuid).build()).contentType(ContentType.JSON)
				.post("api/slogans");

		// Add third slogan
		RestAssuredMockMvc.given().standaloneSetup(this.controller)
				.body(Slogan.builder().title("title3").text("text").userId(uuid).build()).contentType(ContentType.JSON)
				.post("api/slogans");

		RestAssuredMockMvc
				// given
				.given().standaloneSetup(this.controller).body(slogan).contentType(ContentType.JSON)

				// when
				.when().post("api/slogans")

				// then
				.then().log().all().statusCode(HttpStatus.BAD_REQUEST.value());
	}
}
