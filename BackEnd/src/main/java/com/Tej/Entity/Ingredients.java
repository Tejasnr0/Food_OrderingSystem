package com.Tej.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ingredients {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	private String name;
	
	@ManyToOne
	private IngredCategory category;
	
	@JsonIgnore
	@ManyToOne
	private Restaurant res;
	
	private boolean Avaible=true;
}
