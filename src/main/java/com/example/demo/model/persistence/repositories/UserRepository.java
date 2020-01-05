package com.example.demo.model.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.persistence.User;

/**
 * Impementation of the JPA CartRepository.
 * 
 * @author traal-devel
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
  /* methods */
  User findByUsername(String username);
  
}
