package com.app.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserData, Long> {
	Optional<UserData> findByUsername(String username);

	Optional<UserData> findByEmail(String email);

	// handy for login with either
	default Optional<UserData> findByUsernameOrEmail(String login) {
		return findByUsername(login).or(() -> findByEmail(login));
	}

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
