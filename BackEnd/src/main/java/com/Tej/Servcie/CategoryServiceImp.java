package com.Tej.Servcie;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tej.Entity.Category;
import com.Tej.Entity.Restaurant;
import com.Tej.Repository.CategoryRepo;

@Service
public class CategoryServiceImp implements CategoryService {

	@Autowired
	private RestaurantService restService;
	
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		if (name == null || name.trim().isEmpty()) {
	        throw new IllegalArgumentException("Category name cannot be null or empty");
	    }
		Restaurant rest=restService.getRestaurantByUserId(userId);
		 if (rest == null) {
		        throw new Exception("Restaurant not found for userId: " + userId);
		    }
		Category cat=new Category();
		cat.setName(name.trim());
		cat.setRes(rest);
		return categoryRepo.save(cat);
	}

	@Override
	public List<Category> findCategoryByRestaId(Long id) throws Exception {
		
		return categoryRepo.findByRestaurantId(id);
	}

	@Override
	public Category findCategoryById(Long Id) throws Exception {
		
		Optional<Category> opt=categoryRepo.findById(Id);
		
		if(opt==null) {
			throw new Exception("No Category found in it");
		}
		return opt.get();
	}

}
