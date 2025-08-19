package com.Tej.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tej.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByCustomerId(Long userId);

    public List<Order> findByRestaurantId(Long restaurantId);
}