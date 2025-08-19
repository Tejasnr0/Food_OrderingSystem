package com.Tej.Servcie;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Tej.Entity.Category;

@Service
public interface CategoryService {

	public Category createCategory(String name,Long userId) throws Exception;
	
	public List<Category> findCategoryByRestaId(Long id) throws Exception;
	
	public Category findCategoryById(Long Id) throws Exception;
}
