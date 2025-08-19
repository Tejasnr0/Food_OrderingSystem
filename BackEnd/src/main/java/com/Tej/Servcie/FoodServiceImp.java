package com.Tej.Servcie;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tej.Entity.Category;
import com.Tej.Entity.Food;
import com.Tej.Entity.Restaurant;
import com.Tej.Repository.FoodRepo;
import com.Tej.Request.CreateFoodRequest;

@Service
public class FoodServiceImp implements FoodService {

	@Autowired
	private FoodRepo foodrepo;
	
	

	@Override
	public Food createFood(CreateFoodRequest req, Category category, Restaurant restauranr) {
		Food food=new Food();
		food.setCat(category);
		food.setRestaurant(restauranr);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIng(req.getIngredtians());
		food.setIsSeasonal(req.getSesasional());
		food.setIsVeg(req.getVegertarin());
		
		Food savedFood= foodrepo.save(food);
		
		restauranr.getFoods().add(savedFood);
		
		 return null;
	}

	@Override
	public void deleteFood(Long FoodId) throws Exception {
		Food food=findFoodById(FoodId);
		food.setRestaurant(null);
		foodrepo.delete(food);;

	}

	@Override
	public List<Food> getResturantsFood(Long restId, boolean isVeg, boolean isNonVeg,boolean isSeasonal, String foodCategory) {
		
		List<Food> foods=foodrepo.findByRestaurantId(restId);
		
		if(isVeg) {
			foods=filterByVegetarian(foods,isVeg);
		}
		if(isNonVeg) {
			foods=filterByNonVeg(foods,isNonVeg);
		}
		if(isSeasonal) {
			foods=filterByseasonal(foods,isSeasonal);
		}
		
		if(foodCategory!=null && !foodCategory.equals("")) {
			foods=filterByCategory(foods,foodCategory);
		}
		return null;
	}

	private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
		
		return foods.stream().filter(food -> food.getCat().equals(foodCategory)).collect(Collectors.toList());
	}

	private List<Food> filterByseasonal(List<Food> foods, boolean isSeasonal) {
		
		return foods.stream().filter(food -> food.getIsSeasonal()==isSeasonal).collect(Collectors.toList());
	}

	private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
		
		return foods.stream().filter(food -> food.getIsVeg()!=true).collect(Collectors.toList());
	}

	private List<Food> filterByVegetarian(List<Food> foods, boolean isVeg) {
		
		return foods.stream().filter(food -> food.getIsVeg()==isVeg).collect(Collectors.toList());
	}

	@Override
	public List<Food> searchFood(String Keyword) {
		 
		return foodrepo.searchFood(Keyword);
	}

	@Override
	public Food findFoodById(Long FoodId) throws Exception {
		Optional<Food> optionalFood=foodrepo.findById(FoodId);
		if (optionalFood==null) {
			throw new Exception("No food found with the given ID");
		}
		return optionalFood.get();
	}

	@Override
	public Food updateAvailibuityStatus(Long foodId) throws Exception {
		Food food=findFoodById(foodId);
		food.setAvaible(!food.getAvaible());
		return foodrepo.save(food);
	}

}
