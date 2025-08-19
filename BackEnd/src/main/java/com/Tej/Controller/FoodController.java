package com.Tej.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Tej.Entity.Food;
import com.Tej.Entity.Restaurant;
import com.Tej.Entity.User;
import com.Tej.Request.CreateFoodRequest;
import com.Tej.Servcie.FoodService;
import com.Tej.Servcie.RestaurantService;
import com.Tej.Servcie.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {

	@Autowired
	private FoodService foodService;
	
	@Autowired
	private RestaurantService restService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/search")
	public  ResponseEntity<List<Food>> searchFood(@RequestParam String name,@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		
		List<Food> food=foodService.searchFood(name);
		
		return new ResponseEntity<List<Food>>(food,HttpStatus.CREATED);
	}
	
	@GetMapping("/restaurant/(restaurantId}")
	public ResponseEntity<List<Food>> getRestaurantFood(
			@RequestParam boolean vagetarian,
			@RequestParam boolean seasonal,
			@RequestParam boolean nonveg,
			@RequestParam(required = false) String food_category,
			@PathVariable Long restaurantId,
			@RequestHeader("Authorization") String jwt) throws Exception {
	User user=userService.findUserByJwtToken(jwt);
	List<Food> foods=foodService.getResturantsFood(restaurantId, vagetarian, nonveg, seasonal, food_category);
	return new ResponseEntity<>(foods, HttpStatus.OK);
	}
}
