package com.Tej.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tej.Entity.IngredCategory;
import com.Tej.Entity.Ingredients;

public interface IngredientItemRepository extends JpaRepository<Ingredients, Long>{
	public List<Ingredients> findByRestaurantId(Long Id);
}
