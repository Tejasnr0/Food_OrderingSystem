package com.Tej.Request;

import java.util.List;

import com.Tej.Entity.Category;
import com.Tej.Entity.Ingredients;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFoodRequest {

	private String name;
	private String Description;
	private Long price;
	
	private Category category;
	private List<String> images;
	
	private Long restID;
	private Boolean vegertarin;
	private Boolean sesasional;
	private List<Ingredients> ingredtians;
}
