package com.Tej.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tej.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

	public List<Category> findByRestaurantId(Long Id);
}
