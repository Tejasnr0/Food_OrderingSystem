package com.Tej.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Tej.Entity.Restaurant;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
	@Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%', :query, '%')) "
			+ "OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")
	List<Restaurant> findBySearchQuery(@Param("query") String query);

	Restaurant findByOwnerId(Long userId);
	
	
}
