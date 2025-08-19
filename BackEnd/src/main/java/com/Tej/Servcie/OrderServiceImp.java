package com.Tej.Servcie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.Tej.Entity.Address;
import com.Tej.Entity.Cart;
import com.Tej.Entity.CartItem;
import com.Tej.Entity.Order;
import com.Tej.Entity.OrderItem;
import com.Tej.Entity.Restaurant;
import com.Tej.Entity.User;
import com.Tej.Repository.AddressRepo;
import com.Tej.Repository.OrderRepository;
import com.Tej.Repository.OrderitemRepository;
import com.Tej.Repository.Userrepo;
import com.Tej.Request.OrderRequest;

public class OrderServiceImp implements OrderService{
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private OrderitemRepository orderItemRepo;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private Userrepo userRepo;
	
	@Autowired
	private RestaurantService restService;
	
	
	@Autowired
	private CartService cartService;

	@Override
	public Order createOrder(OrderRequest order, User user) throws Exception {
		Address address=order.getDelivery();
		
		Address savedAddress=addressRepo.save(address);
		
		if(!user.getAddress().contains(savedAddress)) {
			user.getAddress().add(savedAddress);
			userRepo.save(user);
		}
		
		Restaurant rest=restService.getRestaurantById(order.getRestaurantId());
			
		Order createOrder=new Order();
		createOrder.setCustomer(user);
		createOrder.setCreatedAt(new Date());
		createOrder.setOrderStatus("Pending");
		createOrder.setDeliveryAddress(savedAddress);
		createOrder.setRes(rest);
		
		Cart cart=cartService.findCartByUserId(user.getId());
		
		List<OrderItem> orderitems=new ArrayList<OrderItem>();
		
		for(CartItem cartItem: cart.getIteam()) {
			OrderItem ot=new OrderItem();
			ot.setFood(cartItem.getFood());
			ot.setIngredi(cartItem.getIngerd());
			ot.setQuantity(cartItem.getQualtiy());
			ot.setTotalPrice(cartItem.getTotalPrice());
			
			orderitems.add(orderItemRepo.save(ot));
		}
		Long totalPrice=cartService.calculateCartTotals(cart);
		
		createOrder.setItems(orderitems);
		createOrder.setTotalPrice(totalPrice);
		
		Order saveorder=orderRepo.save(createOrder);
		rest.getOrders().add(saveorder);
		
		return createOrder;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		Order order = findOrderById(orderId);
	    
	    if (orderStatus.equals("OUT_FOR_DELIVERY") ||
	        orderStatus.equals("DELIVERED") ||
	        orderStatus.equals("COMPLETED") ||
	        orderStatus.equals("PENDING")) {

	        order.setOrderStatus(orderStatus);
	        return orderRepo.save(order);
	    }

	    throw new Exception("Please select a valid order status");
	}

	@Override
	public void calcelOrder(Long orderId) throws Exception {
		Order order=findOrderById(orderId);
		orderRepo.deleteById(orderId);
	}

	@Override
	public List<Order> getUsersOrder(Long userId) throws Exception {
		
		return orderRepo.findByCustomerId(userId);
	}

	@Override
	public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
		  List<Order> orders = orderRepo.findByRestaurantId(restaurantId);

		    if (orderStatus != null) {
		        orders = orders.stream()
		                       .filter(order -> order.getOrderStatus().equals(orderStatus))
		                       .collect(Collectors.toList());
		    }

		    return orders;
	}

	@Override
	public Order findOrderById(Long orderId) throws Exception {
		 Optional<Order> optionalOrder = orderRepo.findById(orderId);

		    if (optionalOrder.isEmpty()) {
		        throw new Exception("Order not found");
		    }

		    return optionalOrder.get();
	}

}
