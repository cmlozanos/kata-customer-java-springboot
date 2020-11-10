package com.example.katacustomerjavaspringboot.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SloganRepository extends JpaRepository<Slogan, UUID> {

	Long countByUserId(UUID userId);

}
