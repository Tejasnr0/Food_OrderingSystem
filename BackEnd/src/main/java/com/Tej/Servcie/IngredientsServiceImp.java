package com.Tej.Servcie;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tej.Entity.IngredCategory;
import com.Tej.Entity.Ingredients;
import com.Tej.Entity.Restaurant;
import com.Tej.Repository.IngredientCategoryRepo;
import com.Tej.Repository.IngredientItemRepository;

@Service
public class IngredientsServiceImp implements IngredientsService{

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private IngredientCategoryRepo ingeCartRepo;
	
	private IngredientItemRepository ingeiItemRepo;
	
	@Override
	public IngredCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		Restaurant rest=restaurantService.getRestaurantById(restaurantId);
		
		IngredCategory ingcat=new IngredCategory();
		ingcat.setRes(rest);
		ingcat.setName(name);
		return ingeCartRepo.save(ingcat) ;
	}

	@Override
	public IngredCategory findIngredientCategoryById(Long id) throws Exception {
		return  ingeCartRepo.findById(id)
	            .orElseThrow(() -> new Exception("No Inge Found"));
	}

	@Override
	public List<IngredCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
		
		return ingeCartRepo.findByRestaurantId(id);
	}

	@Override
	public Ingredients createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception {
		Restaurant rest=restaurantService.getRestaurantById(restaurantId);
		Ingredients ing=new Ingredients();
		IngredCategory cat=findIngredientCategoryById(categoryId);
		
		ing.setName(ingredientName);
		ing.setRes(rest);
		ing.setCategory(cat);
		Ingredients saveding=ingeiItemRepo.save(ing);
		cat.getInge().add(saveding);
		
		return saveding;
	}

	@Override
	public List<Ingredients> findRestaurantsIngredients(Long restaurantId) {
		
		return ingeiItemRepo.findByRestaurantId(restaurantId) ;
	}

	@Override
	public Ingredients updateStock(Long id) throws Exception {
		Optional<Ingredients> optinalItem=ingeiItemRepo.findById(id);
		
		Ingredients savedi=ingeiItemRepo.findById(id).orElseThrow(()-> new Exception("No IngredientItemFound"));
		savedi.setAvaible(!savedi.isAvaible());
		return savedi;
	}

}
