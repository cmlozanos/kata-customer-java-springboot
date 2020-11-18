package com.example.katacustomerjavaspringboot.web.controller;

import com.example.katacustomerjavaspringboot.domain.User;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenRequestWithoutAuthenticationWhenAddResourceThenShouldReturnForbidden() {
        final User user = User.builder().name("name").lastName("lastName").address("street").city("city")
                .email("sample@email.com").build();

        RestAssuredMockMvc
                // given
                .given()
                .mockMvc(mockMvc)
                .postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("invalid", "invalidpwd"))
                .body(user).contentType(ContentType.JSON)

                // when
                .when().post("api/users")

                // then
                .then().log().all().statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void givenPostRequestWhenAddResourceThenShouldAddUser() {
        final User user = User.builder().name("name").lastName("lastName").address("street").city("city")
                .email("sample@email.com").build();

        RestAssuredMockMvc
                // given
                .given()
                .mockMvc(mockMvc)
                .postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword"))
                .body(user).contentType(ContentType.JSON)

                // when
                .when().post("api/users")

                // then
                .then().log().all().statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void givenInvalidUserIdRequestWhenFindByIdResourceThenShouldReturnNotFound() {
        final UUID uuid = UUID.randomUUID();

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("uuid", uuid.toString());

        RestAssuredMockMvc
                // given
                .given()
                .mockMvc(mockMvc)
                .postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword"))

                // when
                .when().get("api/users/{uuid}", params)

                // then
                .then().log().all().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenUserIdRequestWhenFindByIdResourceThenShouldReturnUser() {

        // given
        final User user = User.builder().name("name").lastName("lastName").address("street").city("city")
                .email("sample@email.com").build();

        final String location = RestAssuredMockMvc
                .given()
                .mockMvc(mockMvc)
                .postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword"))
                .body(user)
                .contentType(ContentType.JSON).post("api/users")
                .getHeader("Location");
        final String uuid = location.replace("api/users/", "");

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("uuid", uuid);

        RestAssuredMockMvc

                .given()
                .mockMvc(mockMvc)
                .postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword"))


                // when
                .when().get("api/users/{uuid}", params)

                // then
                .then().log().all().statusCode(HttpStatus.OK.value()).body("name", CoreMatchers.equalTo("name"))
                .body("lastName", CoreMatchers.equalTo("lastName")).body("address", CoreMatchers.equalTo("street"))
                .body("city", CoreMatchers.equalTo("city")).body("email", CoreMatchers.equalTo("sample@email.com"));
    }

    @Test
    void givenUserIdRequestWhenUpdateResourceThenShouldUpdateUserInformation() {

        // given
        final User user = User.builder().name("name").lastName("lastName").address("street").city("city")
                .email("sample@email.com").build();

        final String location = RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword"))
                .body(user).contentType(ContentType.JSON).post("api/users")
                .getHeader("Location");
        final String uuid = location.replace("api/users/", "");

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("uuid", uuid);

        final User userUpdate = User.builder().name("nameUpdate").lastName("lastNameUpdate").address("streetUpdate")
                .city("cityUpdate").email("sampleUpdate@email.com").build();

        RestAssuredMockMvc
                .given()
                .mockMvc(mockMvc)
                .postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword"))

                .body(userUpdate).contentType(ContentType.JSON)

                // when
                .when().put("api/users/{uuid}", params)

                // then
                .then().log().all().statusCode(HttpStatus.OK.value()).body("name", CoreMatchers.equalTo("nameUpdate"))
                .body("lastName", CoreMatchers.equalTo("lastNameUpdate"))
                .body("address", CoreMatchers.equalTo("streetUpdate")).body("city", CoreMatchers.equalTo("cityUpdate"))
                .body("email", CoreMatchers.equalTo("sampleUpdate@email.com"));
    }

    @Test
    void givenInvalidUserIdRequestWhenUpdateResourceThenShouldReturnNotFound() {

        // given
        final String uuid = UUID.randomUUID().toString();

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("uuid", uuid);

        final User userUpdate = User.builder().name("nameUpdate").lastName("lastNameUpdate").address("streetUpdate")
                .city("cityUpdate").email("sampleUpdate@email.com").build();

        RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .postProcessors(SecurityMockMvcRequestPostProcessors.httpBasic("katauser", "katapassword"))
                .body(userUpdate).contentType(ContentType.JSON)

                // when
                .when().put("api/users/{uuid}", params)

                // then
                .then().log().all().statusCode(HttpStatus.NOT_FOUND.value());
    }

}
