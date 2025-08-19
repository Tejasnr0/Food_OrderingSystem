package com.Tej.Request;

import java.util.List;

import com.Tej.DTO.RestDTO;
import com.Tej.Entity.Address;
import com.Tej.Entity.ContactInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRestaurantRequest {

	private Long Id;
	private String name;
	private String description;
	private String cusionType;
	private ContactInfo contac;
	private Address address;
	private String OpeningHourse;
	private List<String> Images;
}
