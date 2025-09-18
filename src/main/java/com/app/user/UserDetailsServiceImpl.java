package com.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	 UserRepo userRepo;

	    // Load user by username (or email)
	    @Override
	    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
	    	  UserData user = userRepo.findByUsernameOrEmail(usernameOrEmail)
	                  .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
	          return user;
	    }
	    
	    public UserData loadById(Long id) {
	        return userRepo.findById(id)
	                .orElseThrow(() -> new UsernameNotFoundException("User id not found: " + id));
	    }

}
