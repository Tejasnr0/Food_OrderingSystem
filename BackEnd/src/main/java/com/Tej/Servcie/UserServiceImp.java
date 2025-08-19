package com.Tej.Servcie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tej.Entity.User;
import com.Tej.Repository.Userrepo;
import com.Tej.config.JwtProvider;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private Userrepo repo;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		User user=repo.findByEmail(email);
		if(user==null) {
			throw new Exception("User not found");
		}
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user=repo.findByEmail(email);
		if(user==null) {
			throw new Exception("user not found");
		}
		return user;
	}

}
