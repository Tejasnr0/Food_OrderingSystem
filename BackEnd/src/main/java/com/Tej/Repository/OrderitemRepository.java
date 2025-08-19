package com.Tej.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tej.Entity.OrderItem;

public interface OrderitemRepository extends JpaRepository<OrderItem, Long>{

}
