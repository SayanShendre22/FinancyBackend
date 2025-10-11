package com.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.JwtService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8082")
public class UserController {
	
	
	@Autowired
	UserRepo ur;
	
	@Autowired
	JwtService jwt;
	
	@Autowired
	UserDetailsServiceImpl uds;
	
	
	@GetMapping("/getUserNameByEmail")
	public ResponseEntity<String> getUsernameByEmail(@RequestParam String email) {
		
		UserData ud = ur.findByEmail(email).get();
		
		if(ud!=null) {
			return new ResponseEntity<String>(ud.getUsername(),HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("user not found",HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/getByUserName")
	public ResponseEntity<Boolean> getByUsername(@RequestParam String username) {
		
		try {
			UserData ud = ur.findByUsername(username).get();
			
			 if (ud!=null) {
			        // return JSON or just a simple flag
			        return ResponseEntity.ok(true); // ✅ Username exists
			    } else {
			        return ResponseEntity.ok(false); // ✅ Username does not exist
			    }
		}catch (Exception e) {
			// TODO: handle exception
			 return ResponseEntity.ok(false); // ✅ Username does not exist
		}
	}
	
	@GetMapping("/getUserByToken/{token}")
	public ResponseEntity<UserData> getByToken(@PathVariable String  token){
		
		System.out.println("here /getUserByToken");
		
		long userid = jwt.extractUserId(token);
		UserData ud=uds.loadById(userid);
		System.out.println(ud);
			 if(ud!=null) {
				  return ResponseEntity.ok(ud);
			 }else {
				  return (ResponseEntity<UserData>) ResponseEntity.notFound();
			 }
	}
	
	@PostMapping("/updateProfile")
	public ResponseEntity<UserData>  updateProfile(@RequestBody UserData user , @RequestHeader("Authorization") String authHeader){
	
		String token = authHeader.replace("Bearer ", "");
		UserData userDetails = ur.getById(jwt.extractUserId(token));
		userDetails.setEmail(user.getEmail());
		userDetails.setUsername(user.getUsername());
		
		if(ur.save(userDetails) != null) {
			return ResponseEntity.ok(userDetails);			
		}
		
		return new ResponseEntity<UserData>(userDetails,HttpStatus.BAD_REQUEST);		
	}
	

}
