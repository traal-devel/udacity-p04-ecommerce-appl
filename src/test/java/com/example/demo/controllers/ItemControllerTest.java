package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.util.TestUtils;

public class ItemControllerTest {

  
  /* member variables */
  private ItemController itemController;
  
  private ItemRepository itemRepository = mock(ItemRepository.class);
  
  
  /* constructors */
  public ItemControllerTest() {
    super();
  }
  
  
  /* methods */
  @Before
  public void setUp() {
    this.itemController = new ItemController();
    TestUtils.injectObject(this.itemController, "itemRepository", this.itemRepository);
  }
  
  @Test
  public void get_items_happy_path() {
    List<Item> itemsExpected = TestUtils.getDefaultItemList(5);
    when(this.itemRepository.findAll()).thenReturn(itemsExpected);
    
    final ResponseEntity<List<Item>> responseItems = this.itemController.getItems();
    
    assertNotNull(responseItems);
    assertEquals(200, responseItems.getStatusCodeValue());
    
    assertEquals(itemsExpected, responseItems.getBody());
  }
  
  @Test
  public void get_item_by_id_happy_path() {
    Item itemExpected = TestUtils.getDefaultItemList(1).get(0);
    when(this.itemRepository.findById(itemExpected.getId()))
                              .thenReturn(Optional.of(itemExpected));
    
    final ResponseEntity<Item> response = 
                      this.itemController.getItemById(itemExpected.getId());
    assertNotNull(response);
    
    Item itemActual = response.getBody();
    
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(itemExpected, itemActual);
    assertEquals(itemExpected.getDescription(), itemActual.getDescription());
    assertEquals(itemExpected.getName(), itemActual.getName());
    assertTrue(itemExpected.equals(itemActual));
    assertTrue(itemExpected.hashCode() == itemActual.hashCode());
  }
  
  @Test
  public void get_items_by_name_happy_path() {
    List<Item> items = TestUtils.getDefaultItemList(2);
    when(this.itemRepository.findByName(any())).thenReturn(items);
    
    final ResponseEntity<List<Item>> response = 
                                  this.itemController.getItemsByName("ITEM");
    
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(items, response.getBody());
  }
  
  @Test
  public void get_items_by_name_repo_items_null() {
    when(this.itemRepository.findByName(any())).thenReturn(null);
    
    final ResponseEntity<List<Item>> response = 
                                  this.itemController.getItemsByName("ITEM");
    
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    assertEquals(null, response.getBody());
  }
  
  @Test
  public void get_items_by_name_repo_items_empty() {
    when(this.itemRepository.findByName(any())).thenReturn(new ArrayList<>());
    
    final ResponseEntity<List<Item>> response = 
                                  this.itemController.getItemsByName("ITEM");
    
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    assertEquals(null, response.getBody());
  }
}
