package com.Tej.config;

import java.util.Collection;
import java.util.Date;
import java.util.List; // ADD THIS IMPORT
import java.util.stream.Collectors; // ADD THIS IMPORT

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private SecretKey Key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	public String generateToken(Authentication auth) {

		System.out.println(auth.getAuthorities());
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		// --- START CHANGE ---
		List<String> roles = populateAuthorities(authorities); // Now returns List<String>
		System.out.println("roles (List): " + roles); // Better logging for clarity

		String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 86400000)) // 24
																														// hours
																														// validity
				.claim("email", auth.getName()).claim("authorities", roles) // Store as a List of Strings directly
				.signWith(Key).compact();
		// --- END CHANGE ---

		return jwt;
	}

	public String getEmailFromJwtToken(String jwt) {
		jwt = jwt.substring(7); // Remove "Bearer " prefix

		Claims claims = Jwts.parserBuilder().setSigningKey(Key).build().parseClaimsJws(jwt).getBody();

		String email = String.valueOf(claims.get("email"));

		return email;
	}

	// --- START CHANGE ---
	// Changed return type from String to List<String>
	private List<String> populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		// Use stream to map GrantedAuthority to String and collect into a List
		return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}
	// --- END CHANGE ---
}