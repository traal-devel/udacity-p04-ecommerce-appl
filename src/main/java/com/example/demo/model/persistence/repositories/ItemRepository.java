package com.example.demo.model.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.persistence.Item;

/**
 * Impementation of the JPA CartRepository.
 * 
 * @author traal-devel
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

  /* methods */
  public List<Item> findByName(String name);

}
