package com.Tej.Servcie;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tej.Entity.Cart;
import com.Tej.Entity.CartItem;
import com.Tej.Entity.Food;
import com.Tej.Entity.User;
import com.Tej.Repository.CartItemRepository;
import com.Tej.Repository.CartRepository;
import com.Tej.Repository.FoodRepo;
import com.Tej.Request.AddCartItemRequest;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private UserService  userSewrvice;
	
	@Autowired
	private CartItemRepository cartItemRepo;
	
	@Autowired
	private FoodService foodService;
	
	
	
	@Override
	public CartItem addIteamToCart(AddCartItemRequest req, String jwt) throws Exception {
		User user=userSewrvice.findUserByJwtToken(jwt);
		Food food = foodService.findFoodById(req.getFoodId());

        Cart cart = cartRepo.findByCustomerId(user.getId());  // ✅ Use actual entity field name

        for (CartItem cartItem : cart.getIteam()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQualtiy() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQualtiy(req.getQuantity());
        newCartItem.setIngerd(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());

        CartItem savedCartItem = cartItemRepo.save(newCartItem);
        
        cart.getIteam().add(savedCartItem);
        
        return savedCartItem;
    }

	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
	    if (cartItemOptional.isEmpty()) {
	        throw new Exception("cart item not found");
	    }
	    CartItem item = cartItemOptional.get();
	    item.setQualtiy(quantity);
	    item.setTotalPrice(item.getFood().getPrice() * quantity);
	    
	    return cartItemRepo.save(item);
	}

	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
		User user=userSewrvice.findUserByJwtToken(jwt);
		Cart cart = cartRepo.findByCustomerId(user.getId());
		
		Optional<CartItem> cartItemOptional = cartItemRepo.findById(cartItemId);
	    if (cartItemOptional.isEmpty()) {
	        throw new Exception("cart item not found");
	    }
	    
	    CartItem cartItem=cartItemOptional.get();
	    
	    cart.getIteam().remove(cartItem);
		return cartRepo.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		Long total=0L;
		
		for(CartItem item:cart.getIteam()) {
			total+=item.getFood().getPrice()*item.getQualtiy();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> optionalCart=cartRepo.findById(id);
		if(optionalCart.isEmpty()) {
			throw new Exception("Cart not found");
		}
		return optionalCart.get();
	} 

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
//		User user=userSewrvice.findUserByJwtToken(jwt);
		Cart cart=cartRepo.findByCustomerId(userId);
		cart.setTotal(calculateCartTotals(cart));
		return cart;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
//		User user=userSewrvice.findUserByJwtToken(jwt);
		Cart cart=findCartByUserId(userId);
		
		cart.getIteam().clear();
		
		return cartRepo.save(cart);
	}

}
