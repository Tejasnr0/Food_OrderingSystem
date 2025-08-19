package com.Tej.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Restaurant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private User owner;
	
	private String name;
	
	private String description;
	
	private String cuisineType;
	
	@Embedded
	private ContactInfo contac;
	
	@OneToOne
	private Address address;

	private String OpeningHourse;
	
	//Remeber that 'res' is the col name in the Order entity we mapping using that
	@OneToMany(mappedBy = "res",cascade = CascadeType.ALL)
	private List<Order> orders=new ArrayList<>();
	
	@ElementCollection
	@Column(length = 1000)
	private List<String> images;
	
	private LocalDateTime registerTime;
	
	private  boolean open;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
	private List<Food> foods=new ArrayList<Food>();
	
	
}
