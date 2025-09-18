package com.app.auth;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OtpModel {
    @Override
	public String toString() {
		return "OtpModel [email=" + email + ", otp=" + otp + ", expiryTime=" + expiryTime + "]";
	}

	@Id
    private String email;

    private String otp;

    private LocalDateTime expiryTime;
    

	public OtpModel(String email, String otp, LocalDateTime expiryTime) {
		this.email = email;
		this.otp = otp;
		this.expiryTime = expiryTime;
	}

	public OtpModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}
    
    
    
}
