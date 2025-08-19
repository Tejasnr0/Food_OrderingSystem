package com.Tej.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tej.Entity.IngredCategory;
import com.Tej.Entity.Ingredients;
import com.Tej.Request.IngredientCategoryReq;
import com.Tej.Request.IngredientItemReq;
import com.Tej.Servcie.IngredientsService;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

	@Autowired
	private IngredientsService ingreService;
	
	
	@PostMapping("/category")
	public ResponseEntity<IngredCategory> createIngredientCategory(@RequestBody IngredientCategoryReq req) throws Exception{
		IngredCategory ingre=ingreService.createIngredientCategory(req.getName(), req.getRestaturantId());
		
		return new ResponseEntity<IngredCategory>(ingre,HttpStatus.CREATED);
	}
	
	@PostMapping("/Item")
	public ResponseEntity<Ingredients> createIngredientItem(@RequestBody IngredientItemReq req) throws Exception{
		Ingredients ingre=ingreService.createIngredientItem(req.getResturId(), req.getName(), req.getCateId());
		
		return new ResponseEntity<Ingredients>(ingre,HttpStatus.CREATED);
	}
	
	@PutMapping("/{Id}/stock")
	public ResponseEntity<Ingredients> updateIngreStock(@PathVariable Long Id) throws Exception{
		Ingredients item=ingreService.updateStock(Id);
		
		return new ResponseEntity<Ingredients>(item,HttpStatus.OK);
	}
	
	public ResponseEntity<List<IngredCategory>> getRestIngreCategory(@PathVariable Long Id) throws Exception{
		List<IngredCategory> items=ingreService.findIngredientCategoryByRestaurantId(Id);
		return new ResponseEntity<List<IngredCategory>>(items,HttpStatus.OK);
	}
	
	
	
}
