package com.Tej.Servcie;

import org.springframework.stereotype.Service;

import com.Tej.Entity.User;


public interface UserService {

	public User findUserByJwtToken(String jwt) throws Exception;
	
	public User findUserByEmail(String email) throws Exception;
}
