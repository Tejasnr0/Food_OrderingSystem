package com.Tej.Request;

import com.Tej.Entity.Address;

import lombok.Data;

@Data
public class OrderRequest {

	private Long restaurantId;
	
	private Address delivery;
}
