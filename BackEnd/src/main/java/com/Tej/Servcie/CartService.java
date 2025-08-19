package com.Tej.Servcie;

import com.Tej.Entity.Cart;
import com.Tej.Entity.CartItem;
import com.Tej.Request.AddCartItemRequest;

public interface CartService {

	public CartItem addIteamToCart(AddCartItemRequest req,String jwt)throws Exception;
	
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;

    public Long calculateCartTotals(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId) throws Exception;
	
	
}
