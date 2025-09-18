package com.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import com.app.user.UserData;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

	private final String SECRET_KEY = "superSecretfzfsgw46ygsgbxyeyghdgdgdgddg4we4t34t3634667Key1232425235636235fwfscsfsffa";

	public String generateToken(UserData user) {
		System.out.println(user.toString());
		
		 return Jwts.builder()
		            .setSubject(String.valueOf(user.getId()))  // subject = immutable id
		            .setIssuedAt(new Date(System.currentTimeMillis()))
		            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
		            .claim("username", user.getUsername())
		            .claim("email", user.getEmail())
		            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
		            .compact();
	}

	// Extract username from token
	public String extractUsername(String token) {
		final Claims claims = extractAllClaims(token);
		return claims.get("username", String.class); // custom field
	}

	public Long extractUserId(String token) {
		return Long.valueOf(extractClaim(token, Claims::getSubject));
	}

	public Claims parseClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
	}

	public boolean isTokenValid(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	// Check if token has expired
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Extract expiration date
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Extract single claim
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Decode and parse all claims from token
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	// Decode the secret key to a `Key` object
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
