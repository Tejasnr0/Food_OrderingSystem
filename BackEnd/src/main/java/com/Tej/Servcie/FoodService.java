package com.Tej.Servcie;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Tej.Entity.Category;
import com.Tej.Entity.Food;
import com.Tej.Entity.Restaurant;
import com.Tej.Request.CreateFoodRequest;

@Service
public interface FoodService {

	public Food createFood(CreateFoodRequest req,Category category,Restaurant restauranr);
	
	public void deleteFood(Long FoodId) throws Exception;
	
	public List<Food> getResturantsFood(Long restId,
										boolean isVeg,
										boolean isNonVeg,
										boolean isSeasonal,
										String foodCategory);
	
	public List<Food> searchFood(String Keyword);
	
	public Food findFoodById(Long FoodId)throws Exception;
	
	public Food updateAvailibuityStatus(Long foodId) throws Exception;
	
	
}
