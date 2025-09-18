package com.app.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.user.UserData;
import com.app.user.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		String path = request.getRequestURI();
		
		if (request.getServletPath().startsWith("/auth") || 
			    request.getServletPath().startsWith("/images")) {
			    filterChain.doFilter(request, response);
			    return;
			}

		
		System.out.println("in filter fun");
		
		final String authHeader = request.getHeader("Authorization");
		final String token;
		final String username;
		System.out.println(authHeader);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			System.out.println("skip JWT validation");
		    filterChain.doFilter(request, response); // skip JWT validation
		    return;
		}
		 
		System.out.println("here");

		token = authHeader.substring(7);
//		username = jwtService.extractUsername(token);
		
		if (jwtService.isTokenValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {

			Long userId = jwtService.extractUserId(token);
			UserData user = userDetailsService.loadById(userId);
			
            var auth = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
		
		System.out.println("End of filter");
		filterChain.doFilter(request, response);
	}
}
