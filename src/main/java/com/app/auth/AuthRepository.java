package com.app.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.user.UserData;

@Repository
public interface AuthRepository extends JpaRepository<UserData, Integer>  {

	@Query("SELECT u FROM UserData u WHERE u.email = :value OR u.username = :value")
	Optional<UserData> findByEmailOrUsername(@Param("value") String value);

	 
	 Optional<UserData> findByEmail(String Email);
	 Optional<UserData> findByUsername(String Email);
	
}
