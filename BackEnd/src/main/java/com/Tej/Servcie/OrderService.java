package com.Tej.Servcie;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Tej.Entity.Order;
import com.Tej.Entity.User;
import com.Tej.Request.OrderRequest;

@Service
public interface OrderService {

	 public Order createOrder(OrderRequest order, User user) throws Exception;

	    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

	    public void calcelOrder(Long orderId) throws Exception;

	    public List<Order> getUsersOrder(Long userId) throws Exception;

	    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception;
	    
	    public Order findOrderById(Long orderId)throws Exception;
	
}
