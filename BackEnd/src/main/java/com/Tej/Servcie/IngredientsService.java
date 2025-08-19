package com.Tej.Servcie;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Tej.Entity.IngredCategory;
import com.Tej.Entity.Ingredients;

@Service
public interface IngredientsService {

    public IngredCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    public Ingredients createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    public List<Ingredients> findRestaurantsIngredients(Long restaurantId);

    public Ingredients updateStock(Long id) throws Exception;
}

