package com.example.demo.controllers;

import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

/**
 * Implementation of the CartController.
 * 
 * @author traal-devel
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {
	
  
  /* member variables */
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	/* constructors */
	public CartController() {
	  super();
	}
	
	
	/* methods */
	/**
	 * Adds items to the cart by using given parameter. 
	 * 
	 * @param request {@link ModifyCartRequest}
	 * @return {@link Cart}
	 */
	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addToCart(
	    @RequestBody ModifyCartRequest request
	 ) {
	  
	  // 1. Step : Find user
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		// 2. Step: Find items by given id.
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(!item.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		// 3. Step: Add items n times.
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
			         .forEach(i -> cart.addItem(item.get()));
		
		// 4. Step: Store into database.
		Cart cartDB = cartRepository.save(cart);
		
		return ResponseEntity.ok(cartDB);
		
	}
	
	/**
	 * Remove an item from existing cart.
	 * 
	 * @param request {@link ModifyCartRequest}
	 * @return {@link Cart}
	 */
	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromCart(
	    @RequestBody ModifyCartRequest request
	) {
	
	  // 1. Step: Find user by given username
	  User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		// 2. Step: Find item by id.
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(!item.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		// 3. Step: Remove item from cart n times.
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.removeItem(item.get()));
		
		// 4. Step: Store current cart in the databasee
		cartRepository.save(cart);
		
		return ResponseEntity.ok(cart);
		
	}
		
}
