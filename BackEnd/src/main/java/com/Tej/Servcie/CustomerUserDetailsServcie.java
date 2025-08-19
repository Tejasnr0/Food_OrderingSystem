package com.Tej.Servcie;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Tej.Entity.User;
import com.Tej.Entity.UserRole; // Make sure this import is correct
import com.Tej.Repository.Userrepo;

@Service
public class CustomerUserDetailsServcie implements UserDetailsService {

	@Autowired
	private Userrepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("--- Inside CustomerUserDetailsServcie.loadUserByUsername ---");
		System.out.println("Attempting to load user with email: " + username);

		User user = userRepo.findByEmail(username);

		if (user == null) {
			System.out.println("User not found for email: " + username);
			throw new UsernameNotFoundException("User not found with email " + username);
		}

		System.out.println("User found: " + user.getEmail() + ", Full Name: " + user.getFullName());

		// *** CRITICAL LOGGING HERE ***
		UserRole role = user.getRole();
		System.out.println("Role retrieved from User entity: " + (role != null ? role.toString() : "NULL (Role was null!)"));

		List<GrantedAuthority> authorities = new ArrayList<>();
		if (role != null) {
			String authorityString = role.toString(); // Get the role string (e.g., "ROLE_ADMIN")
			authorities.add(new SimpleGrantedAuthority(authorityString));
			System.out.println("Authority added to list: " + authorityString);
		} else {
			System.err.println("WARNING: User " + username + " has a NULL role. No authorities will be granted for this user.");
		}

		System.out.println("Final list of authorities to be set in UserDetails: " + authorities);

		// Creating Spring Security UserDetails object
		org.springframework.security.core.userdetails.User userDetails =
				new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

		System.out.println("UserDetails created. Granted Authorities: " + userDetails.getAuthorities());
		System.out.println("--- Exiting CustomerUserDetailsServcie.loadUserByUsername ---");

		return userDetails;
	}
}