package com.Tej.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tej.Entity.Food;
import com.Tej.Entity.Restaurant;
import com.Tej.Entity.User;
import com.Tej.Request.CreateFoodRequest;
import com.Tej.Response.MessageResponse;
import com.Tej.Servcie.FoodService;
import com.Tej.Servcie.RestaurantService;
import com.Tej.Servcie.UserService;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

	@Autowired
	private FoodService foodService;
	
	@Autowired
	private RestaurantService restService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public  ResponseEntity<Food> creatFood(@RequestBody CreateFoodRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Restaurant resta=restService.getRestaurantByUserId(user.getId());
		
		Food food=foodService.createFood(req, req.getCategory(), resta);
		
		return new ResponseEntity<Food>(food,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{Id}")
	public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long Id,@RequestHeader("Authorization") String jwt) throws Exception{
//		User user=userService.findUserByJwtToken(jwt);
	
		
		foodService.deleteFood(Id);
		
		MessageResponse res=new MessageResponse();
		res.setMessage("Food Deleted Successfully");
		
		return new ResponseEntity<MessageResponse>(res,HttpStatus.OK);
	}
	
	@PutMapping("update/{ID}")
	public ResponseEntity<Food> updateFoodAvability(@PathVariable Long Id,@RequestHeader("Authorization") String jwt) throws Exception{
		Food food=foodService.updateAvailibuityStatus(Id);
		
		return new ResponseEntity<Food>(food,HttpStatus.OK);
	}
}
