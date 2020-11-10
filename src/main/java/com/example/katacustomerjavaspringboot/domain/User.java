package com.example.katacustomerjavaspringboot.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class User {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length = 36, columnDefinition = "varchar", updatable= false, nullable=false)
	private UUID id;
	private String name;
	private String lastName;
	private String address;
	private String city;
	private String email;
}
