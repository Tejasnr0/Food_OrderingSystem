package com.Tej.Entity;

import java.util.ArrayList;
import java.util.List;

import com.Tej.DTO.RestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class User {

	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	private String fullName;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "customer" )
	private List<Order> orders = new ArrayList<Order>();
	
	@ElementCollection
	private List<RestDTO> favo=new ArrayList<RestDTO>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> address= new ArrayList<Address>();
	
	
	
}
