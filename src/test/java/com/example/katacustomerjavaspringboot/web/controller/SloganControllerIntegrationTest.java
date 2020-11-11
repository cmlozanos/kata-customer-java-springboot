package com.example.katacustomerjavaspringboot.web.controller;

import java.util.UUID;
import java.util.regex.Pattern;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.exceptions.CustomResponseEntityExceptionHandler;
import com.example.katacustomerjavaspringboot.web.dto.SloganDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SloganControllerIntegrationTest {

	@Autowired
	SloganController controller;

	@Autowired
	UserController userController;

	@Autowired
	CustomResponseEntityExceptionHandler handler;

	@Test
	void givenUserIdAndSloganAndLowerThan3SlogansWhenAddResourceThenShouldAddVerifyAmountAndAddSlogan() {

		final User user = User.builder().name("name").lastName("lastName").address("street").city("city")
				.email("sample@email.com").build();

		final ExtractableResponse<MockMvcResponse> extract = RestAssuredMockMvc.given()
				.standaloneSetup(this.userController).body(user).contentType(ContentType.JSON).post("api/users").then()
				.log().all().statusCode(HttpStatus.CREATED.value()).extract();
		final String userId = extract.header("Location").replace("api/users/", "");
		final UUID uuid = UUID.fromString(userId);
		final SloganDTO slogan = SloganDTO.builder().title("title").text("text").userId(uuid).build();

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
		final User user = User.builder().name("name").lastName("lastName").address("street").city("city")
				.email("sample@email.com").build();

		final ExtractableResponse<MockMvcResponse> extract = RestAssuredMockMvc.given()
				.standaloneSetup(this.userController).body(user).contentType(ContentType.JSON).post("api/users").then()
				.log().all().statusCode(HttpStatus.CREATED.value()).extract();
		final String userId = extract.header("Location").replace("api/users/", "");
		final UUID uuid = UUID.fromString(userId);

		final SloganDTO slogan = SloganDTO.builder().title("title").text("text").userId(uuid).build();

		// Add first slogan
		RestAssuredMockMvc.given().standaloneSetup(this.controller)
				.body(SloganDTO.builder().title("title1").text("text").userId(uuid).build())
				.contentType(ContentType.JSON).post("api/slogans");

		// Add second slogan
		RestAssuredMockMvc.given().standaloneSetup(this.controller)
				.body(SloganDTO.builder().title("title2").text("text").userId(uuid).build())
				.contentType(ContentType.JSON).post("api/slogans");

		// Add third slogan
		RestAssuredMockMvc.given().standaloneSetup(this.controller)
				.body(SloganDTO.builder().title("title3").text("text").userId(uuid).build())
				.contentType(ContentType.JSON).post("api/slogans");

		final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setObjectMapper(new ObjectMapper()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).registerModule(new JavaTimeModule()));

		final Pattern timestampPattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3})");

		RestAssuredMockMvc
				// given
				.given()
				.standaloneSetup(MockMvcBuilders.standaloneSetup(this.controller, this.handler)
						.setMessageConverters(mappingJackson2HttpMessageConverter))
				.body(slogan).contentType(ContentType.JSON)

				// when
				.when().post("api/slogans")

				// then
				.then().log().all().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("timestamp", Matchers.matchesPattern(timestampPattern)).body("code", CoreMatchers.equalTo("001"))
				.body("message", CoreMatchers.equalTo("Max slogans per user exceed. Only allow 3 per user"));
	}
}
