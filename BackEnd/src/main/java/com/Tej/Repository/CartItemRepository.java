package com.Tej.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tej.Entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
