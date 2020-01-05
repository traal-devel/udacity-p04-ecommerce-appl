package com.example.demo.model.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;

/**
 * Impementation of the JPA CartRepository.
 * 
 * @author traal-devel
 */
public interface OrderRepository extends JpaRepository<UserOrder, Long> {
	
  /* methods */
  List<UserOrder> findByUser(User user);
  
}
