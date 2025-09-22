package com.app.auth;

import java.time.LocalDateTime;

public class SignUpUser {

	private String email;
	private String username;
	private String password;
	private LocalDateTime registeredAt = LocalDateTime.now();
	
	
	public SignUpUser() {
	}
	public SignUpUser(String email, String username, String Password, LocalDateTime registeredAt) {
		this.email = email;
		this.username = username;
		this.password = Password;
		this.registeredAt = registeredAt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassward(String Password) {
		password = Password;
	}
	public LocalDateTime getRegisteredAt() {
		return registeredAt;
	}
	public void setRegisteredAt(LocalDateTime registeredAt) {
		this.registeredAt = registeredAt;
	}
	@Override
	public String toString() {
		return "SignUpUser [email=" + email + ", username=" + username + ", password=" + password + ", registeredAt="
				+ registeredAt + "]";
	}
	
	
	
	
}
