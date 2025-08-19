package com.Tej.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tej.Entity.Restaurant;
import com.Tej.Entity.User;
import com.Tej.Request.CreateRestaurantRequest;
import com.Tej.Response.MessageResponse;
import com.Tej.Servcie.RestaurantService;
import com.Tej.Servcie.UserService;

@RestController
@RequestMapping("/api/admin/Restaurants")
public class AdminRestaurantController {

	@Autowired
	private RestaurantService restservice;
	
	@Autowired
	private UserService usersService;
	
	@PostMapping()
	public ResponseEntity<Restaurant> createRestsauant(
			@RequestBody CreateRestaurantRequest res,
			@RequestHeader("Authorization")String Jwt) throws Exception {
			User user=usersService.findUserByJwtToken(Jwt);
			
			Restaurant rest=restservice.createRestaurant(res, user);
			
		return new ResponseEntity<Restaurant>(rest,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Restaurant> UpdateRestsauant(
			@RequestBody CreateRestaurantRequest res,
			@RequestHeader("Authorization")String Jwt,
			@PathVariable Long Id) throws Exception {
		
//			User user=usersService.findUserByJwtToken(Jwt);
			
			Restaurant rest=restservice.updateRestaurant(res, Id);
			
		return new ResponseEntity<Restaurant>(rest,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> DeleteRestsauant(
			@RequestBody CreateRestaurantRequest res,
			@RequestHeader("Authorization")String Jwt,
			@PathVariable Long id) throws Exception {
	
//			User user=usersService.findUserByJwtToken(Jwt);
			
			restservice.deleteResttaurant(id);
			
			Restaurant rest=restservice.getRestaurantById(id);
			
			MessageResponse respo=new MessageResponse();
			
			if(rest!=null) {
				respo.setMessage("Resturnat was deleted");
			}
			else {
				respo.setMessage("Resturnat doesnt exist");
			}
			
		return new ResponseEntity<>(respo,HttpStatus.OK);
	}
	@PutMapping("/{id}/status")
	public ResponseEntity<Restaurant> UpdateRestsauantstatus(
			@RequestBody CreateRestaurantRequest res,
			@RequestHeader("Authorization")String Jwt,
			@PathVariable Long id) throws Exception {
		
//			User user=usersService.findUserByJwtToken(Jwt);
			
			Restaurant rest=restservice.updateRestaurantStatus(id);
			
		return new ResponseEntity<>(rest,HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<Restaurant> findResturantByuserId(
			@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt)  throws Exception {

			User user=usersService.findUserByJwtToken(jwt);
			
			Restaurant res=restservice.getRestaurantByUserId(user.getId());
			
		return new ResponseEntity<Restaurant>(res,HttpStatus.OK);
	}
}
