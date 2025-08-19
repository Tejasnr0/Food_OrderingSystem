package com.Tej.Servcie;

import java.util.List;

import com.Tej.DTO.RestDTO;
import com.Tej.Entity.Restaurant;
import com.Tej.Entity.User;
import com.Tej.Request.CreateRestaurantRequest;

public interface RestaurantService {

	public Restaurant createRestaurant(CreateRestaurantRequest req,User user);
	
	public Restaurant updateRestaurant(CreateRestaurantRequest req,Long Id) throws Exception;
	
	public void deleteResttaurant(Long Id) throws Exception;
	
	public List<Restaurant> getAllRestaurants(); //this is only for the admins
	
	public List<Restaurant> searchRestaurnt(String KeyWord);
	
	public Restaurant getRestaurantByUserId(Long userID)throws Exception;
	
	public Restaurant getRestaurantById(Long ResID) throws Exception;
	
	public RestDTO  addtoFav(Long restID,User user) throws Exception;
	
	public Restaurant updateRestaurantStatus(Long id) throws Exception;
	
	
}
