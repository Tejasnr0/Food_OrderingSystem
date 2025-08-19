package com.Tej.Controller;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tej.Entity.Cart;
import com.Tej.Entity.User;
import com.Tej.Repository.CartRepository;
import com.Tej.Repository.Userrepo;
import com.Tej.Request.LoginRequest;
import com.Tej.Response.AuthoResponse;
import com.Tej.Servcie.CustomerUserDetailsServcie;
import com.Tej.config.JwtProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private Userrepo userrepo;

	@Autowired
	private PasswordEncoder passwordencoder;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private CustomerUserDetailsServcie customeruserdeatil;

	@Autowired
	private CartRepository cartRepo;

	@PostMapping("/signup")
	public ResponseEntity<AuthoResponse> createUserHandler(@RequestBody User user) {
		User isEmailExist = userrepo.findByEmail(user.getEmail());
		if (isEmailExist != null) {
			return ResponseEntity.badRequest().body(new AuthoResponse(null, "Email is already in use", null));
		}

		User createdUser = new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setRole(user.getRole());
		createdUser.setPassword(passwordencoder.encode(user.getPassword()));

		User savedUser = userrepo.save(createdUser);

		Cart cart = new Cart();

		cart.setCustomer(savedUser);

		cartRepo.save(cart);

		// --- CRITICAL CHANGE START ---
				// After saving the user, load their UserDetails to get their authorities
				UserDetails userDetails = customeruserdeatil.loadUserByUsername(savedUser.getEmail());

				// Create the Authentication object with the loaded userDetails and its authorities
				Authentication authentication = new UsernamePasswordAuthenticationToken(
				    userDetails, // Principal is the UserDetails object
				    null, // Credentials are null for token-based auth
				    userDetails.getAuthorities() // Get authorities from UserDetails
				);
				// --- CRITICAL CHANGE END ---
		
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateToken(authentication);

		AuthoResponse authoResponse = new AuthoResponse(jwt, "Registration successful", savedUser.getRole());

		return new ResponseEntity<AuthoResponse>(authoResponse, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthoResponse> singin(@RequestBody LoginRequest req) {

		String username = req.getEmail();
		String password = req.getPassword();
		

		Authentication authentication = authencation(username, password);
		
		String jwt = jwtProvider.generateToken(authentication);
		
		Collection<? extends GrantedAuthority> autorites=authentication.getAuthorities();
		
		User loginedUser=userrepo.findByEmail(username);
		
		
		AuthoResponse authoResponse = new AuthoResponse(jwt, "Login successful", loginedUser.getRole());

		return new ResponseEntity<AuthoResponse>(authoResponse, HttpStatus.OK);

	}

	private Authentication authencation(String username, String password) {

		UserDetails userdetails = customeruserdeatil.loadUserByUsername(username);

		if (userdetails == null) {
			throw new BadCredentialsException("invalid");
		}
		if (!passwordencoder.matches(password, userdetails.getPassword())) {
			throw new BadCredentialsException("invalid ");
		}

		return new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
	}
}
