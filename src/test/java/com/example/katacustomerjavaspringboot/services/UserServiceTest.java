package com.example.katacustomerjavaspringboot.services;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.katacustomerjavaspringboot.domain.User;
import com.example.katacustomerjavaspringboot.domain.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@InjectMocks
	UserService service;

	@Mock
	UserRepository repository;

	@Test
	void givenUserWhenCreateThenShouldReturnOk() {
		// given
		final User user = User.builder().name("name").lastName("lastname").address("street").city("city")
				.email("sample@email.com").build();

		Mockito.when(this.repository.save(user)).thenReturn(User.builder().id(UUID.randomUUID()).name("name")
				.lastName("lastname").address("street").city("city").email("sample@email.com").build());

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

	@Test
	void givenUserIdWhenFindByIdThenShouldReturnOk() {
		// given
		final UUID uuid = UUID.randomUUID();

		final User mockUser = User.builder().id(uuid).name("name").lastName("lastname").address("street").city("city")
				.email("sample@email.com").build();
		Mockito.when(this.repository.findById(uuid)).thenReturn(Optional.of(mockUser));

		// when
		final Optional<User> userFindById = this.service.findById(uuid);

		// then
		Assertions.assertTrue(userFindById.isPresent());
		Assertions.assertEquals(uuid, userFindById.get().getId());
		Assertions.assertEquals(mockUser.getName(), userFindById.get().getName());
		Assertions.assertEquals(mockUser.getLastName(), userFindById.get().getLastName());
		Assertions.assertEquals(mockUser.getAddress(), userFindById.get().getAddress());
		Assertions.assertEquals(mockUser.getCity(), userFindById.get().getCity());
		Assertions.assertEquals(mockUser.getEmail(), userFindById.get().getEmail());
	}

	@Test
	void givenInvalidUserIdWhenFindByIdThenShouldReturnEmptyValue() {
		// given
		final UUID uuid = UUID.randomUUID();

		// when
		final Optional<User> userFindById = this.service.findById(uuid);

		// then
		Assertions.assertFalse(userFindById.isPresent());
	}

	@Test
	void givenUserIdAndUserWhenUpdateByIdThenShouldReturnOk() {
		// given
		final UUID uuid = UUID.randomUUID();

		final User mockUser = User.builder().id(uuid).name("name").lastName("lastname").address("street").city("city")
				.email("sample@email.com").build();
		Mockito.when(this.repository.findById(uuid)).thenReturn(Optional.of(mockUser));

		final User mockUserToUpdate = User.builder().id(uuid).name("nameUpdated").lastName("lastnameUpdated")
				.address("streetUpdated").city("city").email("sampleUpdated@email.com").build();

		Mockito.when(this.repository.save(mockUserToUpdate)).thenReturn(mockUserToUpdate);

		// when
		final Optional<User> userUpdated = this.service.update(uuid, mockUserToUpdate);

		// then
		Assertions.assertTrue(userUpdated.isPresent());
		Assertions.assertEquals(uuid, userUpdated.get().getId());
		Assertions.assertEquals(mockUserToUpdate.getName(), userUpdated.get().getName());
		Assertions.assertEquals(mockUserToUpdate.getLastName(), userUpdated.get().getLastName());
		Assertions.assertEquals(mockUserToUpdate.getAddress(), userUpdated.get().getAddress());
		Assertions.assertEquals(mockUserToUpdate.getCity(), userUpdated.get().getCity());
		Assertions.assertEquals(mockUserToUpdate.getEmail(), userUpdated.get().getEmail());
	}

	@Test
	void givenInvalidUserIdWhenUpdateByIdThenShouldReturnEmptyValue() {
		// given
		final UUID uuid = UUID.randomUUID();
		final User user = User.builder().build();

		// when
		final Optional<User> userUpdateById = this.service.update(uuid, user);

		// then
		Assertions.assertFalse(userUpdateById.isPresent());
	}
}
