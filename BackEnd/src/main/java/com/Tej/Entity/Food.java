package com.Tej.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale.Category;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String description;
	
	private Long price;
	
	@ManyToOne
	private com.Tej.Entity.Category cat;
	
	@Column(length = 1000)
	@ElementCollection
	private List<String> images;
	
	private Boolean avaible;
	
	@ManyToOne
	private Restaurant restaurant;
	
	private Boolean isVeg;
	
	private Boolean isSeasonal;
	
	@ManyToMany
	private List<Ingredients> ing=new ArrayList<>();
	
	private Date createdDate;
	
}
