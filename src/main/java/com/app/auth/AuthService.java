package com.app.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.user.UserData;

@Service
public class AuthService {
	
	@Autowired
	AuthRepository authRepo;
	
	@Autowired
	OtpController otpController;
	
	public String signUp(SignUpUser user) {
		
		try {
			if( authRepo.findByEmail(user.getEmail()).get() != null  ) {
				System.out.println("Email allready registerd");
				return "registered";
			}
		}catch (Exception e) {
			System.out.println("No email found");
		}
		
//		try {
//			if(authRepo.findByUsername(user.getUsername()).get() != null ) {
//				System.out.println("Username allready present try diffrent one");
//				return false;
//			}
//		}catch (Exception e) {
//			System.out.println("No username found");
//		}
		
		UserData newUser = new UserData();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setRegisteredAt(user.getRegisteredAt());
		newUser.setUsername(user.getUsername());
		
	
		UserData u = authRepo.save(newUser);
		
		if(u!=null) {
			return "done";
		}
		
		return "not done";
	}
	
	public UserData loginUser(AuthModel auth) {
		
		Optional<UserData> user = authRepo.login(auth.getEmail(), auth.getEmail(), auth.getPassword());
		
		try {
			if(user.get()!=null) {
				return user.get();
			}
		}catch (Exception e) {
			return null;
		}
		
		return null;
	}
	
}
