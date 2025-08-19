package com.Tej.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tej.Entity.Food;

public interface FoodRepo extends JpaRepository<Food, Long> {

	List<Food> findByRestaurantId(Long restaurantId);
	
	@Query("SELECT F FROM Food WHERE F.name LIKE %:Keyword% OR f.foodCategory.name LIKE %:Keyword%")
	List<Food> searchFood(@Param("Keyword") String Keyword);
}
