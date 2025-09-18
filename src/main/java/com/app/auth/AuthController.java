package com.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.JwtService;
import com.app.user.UserData;
import com.app.user.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

	@Autowired
	AuthService authService;
	
	@Autowired
	JwtService jwt;
	
	@Autowired
	AuthenticationManager manager;
	
	@Autowired
	UserRepo ur;

	@RequestMapping("/hello")
	public String hello() {
		return "Hello this is an test API";
	}

	@RequestMapping("/register")
	public ResponseEntity<TokenModel> register(@RequestBody SignUpUser user) {
		String resp =authService.signUp(user);
		// also take username while registration in future
		
		if(resp.equalsIgnoreCase("registered")) {
			//mail already registered
			return new ResponseEntity<TokenModel>(HttpStatus.BAD_REQUEST);
		}else if(resp.equalsIgnoreCase("not done")) {
			 new ResponseEntity<String>("not done",HttpStatus.BAD_REQUEST);
		}
		UserData newuser  = ur.findByUsername(user.getEmail()).get();
		String token = jwt.generateToken(newuser);
		return new ResponseEntity<TokenModel>(new TokenModel(token), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenModel> login(@RequestBody AuthModel auth, HttpServletRequest request) {
		
//		UserData u = authService.loginUser(auth);
//		boolean u = this.doAuthenticate(auth.getEmail(), auth.getPassword());
		
		UserData u = authService.loginUser(auth);
		
		if (u!=null) {
			System.err.println("Login successfull...!");
			
			HttpSession session = request.getSession();
			session.setAttribute("user", u);
			
			System.out.println(u.toString());
			
			String token = jwt.generateToken(u);
			
			return new ResponseEntity<TokenModel>(new TokenModel(token), HttpStatus.OK);
		} else {
			System.out.println("Invalid crediential");
			return new ResponseEntity<TokenModel>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// Invalidate the session
		session.invalidate();
		return "logged_out";
	}

	public Boolean doAuthenticate(String name, String pass) {

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(name, pass);

		try {
			manager.authenticate(auth);
			System.out.println("doneee...");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
