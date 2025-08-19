package com.Tej.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tej.Entity.User;
import com.Tej.Servcie.UserService;


@RequestMapping("/users")
@RestController
public class UserController {

	@Autowired
	private UserService userser;
	
	@GetMapping("/profile")
	public ResponseEntity<?> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception{
		 try {
		        User user = userser.findUserByJwtToken(jwt);
		        return new ResponseEntity<User>(user, HttpStatus.OK);
		    } catch (Exception e) {
		        return new ResponseEntity<String>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		    }
	}
}
