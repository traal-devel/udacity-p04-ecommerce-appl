package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

/**
 * Implementation of the UserController.
 * 
 * @author traal-devel
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
	
  
  /* member variables */
	@Autowired
	private UserRepository   userRepository;
	
	@Autowired
	private CartRepository   cartRepository;

	
	/* constructors */
	/**
	 * Default constructor for UserController.
	 */
	public UserController() {
    super();
  }
	
	
	/* methods */
	/**
	 * Finds user by given id.
	 * 
	 * @param id Long
	 * @return {@link User}
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(
	    @PathVariable Long id
	) {
	
	  return ResponseEntity.of(userRepository.findById(id));
	  
	}
	
	/**
	 * Finds user by given username.
	 * 
	 * @param username String
	 * @return {@link User}
	 */
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(
	    @PathVariable String username
	) {
	
	  User user = userRepository.findByUsername(username);
		return user == null ? 
		            ResponseEntity.notFound().build() : 
	              ResponseEntity.ok(user);

	}
	
	/**
	 * Create user by using given parameter.
	 * 
	 * @param createUserRequest {@link CreateUserRequest}
	 * @return {@link User}
	 */
	@PostMapping("/create")
	public ResponseEntity<User> createUser(
	    @RequestBody CreateUserRequest createUserRequest
	) {
	  
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		
		Cart cart = new Cart();
		cartRepository.save(cart);
		
		user.setCart(cart);
		userRepository.save(user);
		
		return ResponseEntity.ok(user);
		
	}
	
}
