package com.example.demo.model.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;

/**
 * Impementation of the JPA CartRepository.
 * 
 * @author traal-devel
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
	
  /* methods */
  Cart findByUser(User user);
  
}
