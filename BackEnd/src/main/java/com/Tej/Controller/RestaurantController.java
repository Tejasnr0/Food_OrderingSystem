 package com.Tej.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Tej.DTO.RestDTO;
import com.Tej.Entity.Restaurant;
import com.Tej.Entity.User;
import com.Tej.Request.CreateRestaurantRequest;
import com.Tej.Servcie.RestaurantService;
import com.Tej.Servcie.UserService;

@RestController
@RequestMapping("/api/Restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurantService restservice;
	
	@Autowired
	private UserService usersService;
	
	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> SearchRestsauant(
			@RequestHeader("Authorization")String Jwt,
			@RequestParam String KeyWord) throws Exception {
//			User user=usersService.findUserByJwtToken(Jwt);
			
			List<Restaurant> restaurants=restservice.searchRestaurnt(KeyWord);
			
			
		return new ResponseEntity<List<Restaurant>>(restaurants,HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<Restaurant>> GetAllSearchRestsauant(
			@RequestHeader("Authorization")String Jwt) throws Exception {
//			User user=usersService.findUserByJwtToken(Jwt);
			
			List<Restaurant> restaurants=restservice.getAllRestaurants();
			
			
		return new ResponseEntity<List<Restaurant>>(restaurants,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findRestsauantById(
			@RequestHeader("Authorization")String Jwt,
			@PathVariable Long id) throws Exception {
//			User user=usersService.findUserByJwtToken(Jwt);
			
			Restaurant restaurants=restservice.getRestaurantById(id);
			if(restaurants==null) {
				throw new Exception("No Restaurants is present by the given ID");
			}
			
			
		return new ResponseEntity<Restaurant>(restaurants,HttpStatus.OK);
	}
	
	@PutMapping("/{id}/add-favorities")
	public ResponseEntity<RestDTO> addToFavorities(
			@RequestHeader("Authorization")String Jwt,
			@PathVariable Long id) throws Exception {
		
			User user=usersService.findUserByJwtToken(Jwt);
			
			RestDTO restaurants=restservice.addtoFav(id,user);
			if(restaurants==null) {
				throw new Exception("No Restaurants is present by the given ID");
			}
			
			
		return new ResponseEntity<RestDTO>(restaurants,HttpStatus.OK);
	}
	
	
}
