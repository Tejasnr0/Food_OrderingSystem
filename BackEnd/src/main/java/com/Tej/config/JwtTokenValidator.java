package com.Tej.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors; // ADD THIS IMPORT

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // ADD THIS IMPORT
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = request.getHeader(JwtConstant.JWT_HEADER);

		// --- START CHANGE ---
		// Always check for "Bearer " prefix and ensure JWT is not null/empty
		if (jwt != null && jwt.startsWith("Bearer ")) {
			jwt = jwt.substring(7); // Remove "Bearer " prefix
			try {
				SecretKey Key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				Claims claims = Jwts.parserBuilder().setSigningKey(Key).build().parseClaimsJws(jwt).getBody();

				String email = String.valueOf(claims.get("email"));

				// CRITICAL: Get authorities as a List<String>
				// JWT libraries typically de-serialize JSON arrays into Java Lists
				List<String> authoritiesList = (List<String>) claims.get("authorities");

				List<GrantedAuthority> auth = null;
				if (authoritiesList != null) {
					// Convert the List<String> roles into a List of SimpleGrantedAuthority objects
					auth = authoritiesList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
				} else {
					// If no authorities claim, provide an empty list to avoid null pointer
					auth = List.of();
				}

				// Create the Authentication object with the extracted email and authorities
				org.springframework.security.core.Authentication authentication = new UsernamePasswordAuthenticationToken(
						email, null, auth);
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (Exception e) {
				// Log the error for debugging. Always good practice.
				System.err.println("JWT Validation Error: " + e.getMessage());
				// Propagate a more informative exception
				throw new BadCredentialsException("Invalid Token or Authorities: " + e.getMessage(), e);
			}
		}
		// --- END CHANGE ---

		filterChain.doFilter(request, response);
	}
}