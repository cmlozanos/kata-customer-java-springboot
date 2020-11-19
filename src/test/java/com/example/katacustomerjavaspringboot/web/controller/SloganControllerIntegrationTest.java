package com.example.katacustomerjavaspringboot.web.controller;

import java.util.UUID;
import java.util.regex.Pattern;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.exceptions.CustomResponseEntityExceptionHandler;
import com.example.katacustomerjavaspringboot.web.dto.SloganDTO;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.ExtractableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SloganControllerIntegrationTest {

	private static final String TIMESTAMP_PATTERN = "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.(\\d{2}|\\d{3}|\\d{4}|\\d{5}|\\d{6}))";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	CustomResponseEntityExceptionHandler handler;

	@Test
	void givenInvalidUserIdAndSloganWhenAddResourceThenShouldAddVerifyVerifyUserIdExists() {

		final UUID uuid = UUID.randomUUID();
		final SloganDTO slogan = SloganDTO.builder().title("title").text("text").userId(uuid).build();

		final Pattern timestampPattern = Pattern.compile(SloganControllerIntegrationTest.TIMESTAMP_PATTERN);

		RestAssuredMockMvc
				// given
				.given().mockMvc(this.mockMvc)
				.postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword")).body(slogan)
				.contentType(ContentType.JSON)

				// when
				.when().post("api/slogans")

				// then
				.then().log().all().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("timestamp", Matchers.matchesPattern(timestampPattern)).body("code", CoreMatchers.equalTo("002"))
				.body("message", CoreMatchers.equalTo("User not found"));
	}

	@Test
	void givenUserIdAndSloganAndLowerThan3SlogansWhenAddResourceThenShouldAddVerifyAmountAndAddSlogan() {

		final User user = User.builder().name("name").lastName("lastName").address("street").city("city")
				.email("sample@email.com").build();

		final ExtractableResponse<MockMvcResponse> extract = RestAssuredMockMvc.given().mockMvc(this.mockMvc)
				.postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword")).body(user)
				.contentType(ContentType.JSON).post("api/users").then().log().all()
				.statusCode(HttpStatus.CREATED.value()).extract();
		final String userId = extract.header("Location").replace("api/users/", "");
		final UUID uuid = UUID.fromString(userId);
		final SloganDTO slogan = SloganDTO.builder().title("title").text("text").userId(uuid).build();

		RestAssuredMockMvc
				// given
				.given().mockMvc(this.mockMvc)
				.postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword")).body(slogan)
				.contentType(ContentType.JSON)

				// when
				.when().post("api/slogans")

				// then
				.then().log().all().statusCode(HttpStatus.CREATED.value());
	}

	@Test
	void givenUserIdAndSloganAnd3SlogansWhenAddResourceThenShouldAddVerifyAmountAndReturnKOResponse() {
		final User user = User.builder().name("name").lastName("lastName").address("street").city("city")
				.email("sample@email.com").build();

		final ExtractableResponse<MockMvcResponse> extract = RestAssuredMockMvc.given().mockMvc(this.mockMvc)
				.postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword")).body(user)
				.contentType(ContentType.JSON).post("api/users").then().log().all()
				.statusCode(HttpStatus.CREATED.value()).extract();
		final String userId = extract.header("Location").replace("api/users/", "");
		final UUID uuid = UUID.fromString(userId);

		final SloganDTO slogan = SloganDTO.builder().title("title").text("text").userId(uuid).build();

		// Add first slogan
		RestAssuredMockMvc.given().mockMvc(this.mockMvc)
				.postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword"))
				.body(SloganDTO.builder().title("title1").text("text").userId(uuid).build())
				.contentType(ContentType.JSON).post("api/slogans");

		// Add second slogan
		RestAssuredMockMvc.given().mockMvc(this.mockMvc)
				.postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword"))
				.body(SloganDTO.builder().title("title2").text("text").userId(uuid).build())
				.contentType(ContentType.JSON).post("api/slogans");

		// Add third slogan
		RestAssuredMockMvc.given().mockMvc(this.mockMvc)
				.postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword"))
				.body(SloganDTO.builder().title("title3").text("text").userId(uuid).build())
				.contentType(ContentType.JSON).post("api/slogans");

		final Pattern timestampPattern = Pattern.compile(SloganControllerIntegrationTest.TIMESTAMP_PATTERN);

		RestAssuredMockMvc
				// given
				.given().mockMvc(this.mockMvc)
				.postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword")).body(slogan)
				.contentType(ContentType.JSON)

				// when
				.when().post("api/slogans")

				// then
				.then().log().all().statusCode(HttpStatus.BAD_REQUEST.value())
				.body("timestamp", Matchers.matchesPattern(timestampPattern)).body("code", CoreMatchers.equalTo("001"))
				.body("message", CoreMatchers.equalTo("Max slogans per user exceed. Only allow 3 per user"));
	}
}
