package com.Tej.config;

import java.util.Arrays;
import java.util.Collections;

import javax.security.sasl.AuthorizeCallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.Tej.Servcie.CustomerUserDetailsServcie;

import jakarta.servlet.http.HttpServletRequest;

@Configuration //Tells Spring this is a configuration class.
@EnableWebSecurity //Enables Spring Security for your app.
public class AppConfig {

	@Autowired
	private CustomerUserDetailsServcie customUserDetailsService;

	@Bean
	public UserDetailsService userDetailsService() {
	    return customUserDetailsService;
	}

	
	//This method defines the main security rules for your app and Spring will call this to build the filter chain.
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		// You’re telling Spring Don’t create sessions (STATELESS) — your app relies on JWT tokens, not sessions.
		http.sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(Authorize -> Authorize //from HERE your start defining Authorization rules
				.requestMatchers("/api/admin/**").hasAnyRole("ADMIN","RESTAURANT_OWNER") //access control rules:URLs starting with /api/admin/ → allowed only for users with ADMIN or RESTAURANT_OWNER roles.
				.requestMatchers("/api/**").authenticated()  //URLs starting with /api/ → user must be logged in (authenticated)
				.anyRequest().permitAll()                    //Everything else → allowed without login (public access).
				).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)  //VIMP:- Adds a custom filter (JwtTokenValidator) that runs before Spring’s basic auth filter to validate your JWT tokens.
		.csrf(csrf->csrf.disable())//🚫 Disable CSRF
		.cors(cors->cors.configurationSource(corsConfigurationSource())); // Enables CORS and applies the custom CORS config you define below.
		return http.build();
	}
	
	private CorsConfigurationSource corsConfigurationSource() {
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cfg =new CorsConfiguration();
				
				//These are the Allowed origins
				cfg.setAllowedOrigins(Arrays.asList(
						"https://localhost:3000",
						"https://myfrontend.com"
						));
				//these are the Allowed crud methods and more 
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				cfg.setMaxAge(3600L);
				return cfg;
			}
		};
	}
	
	//this used to take the password entered by the user and encrypt it store to the DB in encrypted form so that when system hacked still the hacker cant get the Password
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
