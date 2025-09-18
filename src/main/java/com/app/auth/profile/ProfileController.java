package com.app.auth.profile;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.JwtService;
import com.app.user.UserData;
import com.app.user.UserRepo;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/profile")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {

	@Autowired
	ProfileService profileService;

	@Autowired
	UserRepo ur;
	
	@Autowired
	JwtService jwt;
	
	
	@GetMapping("/getProfile")
	public ResponseEntity<ProfileModel> getProfile( @RequestHeader("Authorization") String authHeader) {
//		System.out.println("here sayan");
		String token = authHeader.replace("Bearer ", "");
		long uid = jwt.extractUserId(token);
		
		ProfileModel p = profileService.getProfile(ur.getById(uid));
//		System.out.println("profile is here "+p.toString());
		if(p!=null) {
			return new ResponseEntity<ProfileModel>(p,HttpStatus.OK);
		}
		return  new ResponseEntity<ProfileModel>(HttpStatus.BAD_REQUEST);
	}
	

	@PostMapping("/saveProfile")
	public String saveProfile(@ModelAttribute ProfileModel profile, @RequestHeader("Authorization") String authHeader)
			throws IOException {
		String token = authHeader.replace("Bearer ", "");
		UserData userDetails = ur.getById(jwt.extractUserId(token));
//		System.out.println(profile.toString()+" "+ userDetails.toString());

		if (userDetails != null) {
			ProfileModel updatedProfile = profileService.SaveProfile(profile, userDetails);
//			System.out.println("wsofgosg " + updatedProfile.toString());
			if (updatedProfile != null) {
				return "Profile updated successfully";
			}

		}

		return "Not able to save profile, some error occured";
	}

}
