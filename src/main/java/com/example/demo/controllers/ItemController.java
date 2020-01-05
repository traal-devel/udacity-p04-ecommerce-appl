package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

/**
 * Implementation of the ItemController
 * 
 * @author traal-devel
 */
@RestController
@RequestMapping("/api/item")
public class ItemController {

  
  /* member variables */
	@Autowired
	private ItemRepository itemRepository;
	
	
	/* constructors */
	public ItemController() {
	  super();
	}
	
	
	/* methods */
	/**
	 * Gets all items from the database.
	 * 
	 * @return List of {@link Item}
	 */
	@GetMapping
	public ResponseEntity<List<Item>> getItems() {
	  
		return ResponseEntity.ok(itemRepository.findAll());
		
	}
	
	/**
	 * Gets item by given id.
	 * 
	 * @param id Long
	 * @return {@link Item}
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(
	    @PathVariable Long id
	) {
	  
		return ResponseEntity.of(itemRepository.findById(id));
		
	}
	
	/**
	 * Gets item by given name
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(
	    @PathVariable String name
	) {
	  
		List<Item> items = itemRepository.findByName(name);
		return items == null || items.isEmpty() ? 
		          ResponseEntity.notFound().build() : 
		          ResponseEntity.ok(items);
			
	}
	
}
