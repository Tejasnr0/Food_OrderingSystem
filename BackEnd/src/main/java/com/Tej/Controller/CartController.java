package com.Tej.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tej.Entity.Cart;
import com.Tej.Entity.CartItem;
import com.Tej.Request.AddCartItemRequest;
import com.Tej.Request.UpdateCartItemReq;
import com.Tej.Servcie.CartService;
import com.Tej.Servcie.UserService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@PutMapping("/cart/add")
	public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		CartItem caritem=cartService.addIteamToCart(req, jwt);
		return new ResponseEntity<CartItem>(caritem,HttpStatus.CREATED);
	}
	
	@PostMapping("/cart-item/add")
	public ResponseEntity<CartItem> updateCart(@RequestBody UpdateCartItemReq req,@RequestHeader("Authorization") String jwt) throws Exception{
		CartItem caritem=cartService.updateCartItemQuantity(req.getCartyItemID(), req.getQuntatiy());
		return new ResponseEntity<CartItem>(caritem,HttpStatus.OK);
	}
	
	@DeleteMapping("/cart-item/{Id}/remove")
	public ResponseEntity<Cart> removeCartItem(@PathVariable Long Id,@RequestHeader("Authorization") String jwt) throws Exception{
		Cart cart=cartService.removeItemFromCart(Id, jwt);
		return  new ResponseEntity<Cart>(cart,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/cart/clear")
	public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt)throws Exception{
		Cart cart=cartService.clearCart(userService.findUserByJwtToken(jwt).getId());
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@GetMapping("/cart")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt)throws Exception{
		Cart cart=cartService.findCartByUserId(userService.findUserByJwtToken(jwt).getId());
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
}
