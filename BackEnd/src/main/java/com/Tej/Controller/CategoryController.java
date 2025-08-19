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
import org.springframework.web.bind.annotation.RestController;

import com.Tej.Entity.Category;
import com.Tej.Entity.Restaurant;
import com.Tej.Entity.User;
import com.Tej.Servcie.CategoryService;
import com.Tej.Servcie.RestaurantService;
import com.Tej.Servcie.UserService;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {

	@Autowired
	private CategoryService catService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestaurantService restServ;
	
	@PostMapping("/create")
	public ResponseEntity<Category> createCategory(@RequestBody Category cat,@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Category cate=catService.createCategory(cat.getName(), user.getId());
		
		return new ResponseEntity<Category>(cate,HttpStatus.CREATED);
	}
	
	@GetMapping("/category/restaurant")
	public ResponseEntity<List<Category>> getRestCat(@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		List<Category> Categoryrest=catService.findCategoryByRestaId(user.getId());
		return new ResponseEntity<List<Category>>(Categoryrest,HttpStatus.OK);
		
	}
}
