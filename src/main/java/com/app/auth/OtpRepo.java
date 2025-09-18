package com.app.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<OtpModel, String> {
	
	OtpModel findByEmail(String email);

}
