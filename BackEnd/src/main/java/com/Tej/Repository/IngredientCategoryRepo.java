package com.Tej.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tej.Entity.IngredCategory;

public interface IngredientCategoryRepo extends JpaRepository<IngredCategory, Long> {

	public List<IngredCategory> findByRestaurantId(Long Id);
}
