package com.Tej.Response;

import com.Tej.Entity.UserRole;

import io.jsonwebtoken.Jwt;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthoResponse {

	private String jwt;
	
	private String message;
	
	private UserRole role;
	
	public AuthoResponse(String jwt, String message, UserRole role) {
        this.jwt = jwt;
        this.message = message;
        this.role = role;
    }

	public AuthoResponse() {
		// TODO Auto-generated constructor stub
	}
}
