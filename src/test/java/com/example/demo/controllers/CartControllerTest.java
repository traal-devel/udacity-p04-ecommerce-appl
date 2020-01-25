package com.example.demo.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.util.TestUtils;

public class CartControllerTest {

  
  /* member variables */
  
  private CartController cartController;

  private UserRepository userRepository = mock(UserRepository.class);
  
  private CartRepository cartRepository = mock(CartRepository.class);
  
  private ItemRepository itemRepository = mock(ItemRepository.class);
  
  private List<Item> mockItems;
  
  private String username = "johndoe";
  
  /* constructors */
  public CartControllerTest() {
    super();
  }
  
  
  /* methods */
  @Before
  public void setUp() {
    this.cartController = new CartController();
    
    TestUtils.injectObject(this.cartController, "userRepository", this.userRepository);
    TestUtils.injectObject(this.cartController, "cartRepository", this.cartRepository);
    TestUtils.injectObject(this.cartController, "itemRepository", this.itemRepository);
  }
  
  private void mockData() {
    User user = TestUtils.createDefaultUserWithCart(this.username);
    this.mockItems = TestUtils.getDefaultItemList(5);
    this.mockItems.forEach(t -> {
      when(itemRepository.findById(t.getId())).thenReturn(Optional.of(t));
    });
    when(this.userRepository.findByUsername(username)).thenReturn(user);
    when(this.cartRepository.save(any())).thenReturn(user.getCart());
    
  }
  
  @Test
  public void add_to_cart_happy_path() {
    this.mockData();
    int iQuantity = 5;
    ModifyCartRequest request = new ModifyCartRequest();
    request.setItemId(this.mockItems.get(2).getId());
    request.setQuantity(iQuantity);
    request.setUsername(this.username);
    
    final ResponseEntity<Cart> response = this.cartController.addToCart(request);
    
    assertNotNull(response);
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    assertTrue(iQuantity == response.getBody().getItems().size());
  }
  
  @Test
  public void add_to_cart_not_found_username() {
    ModifyCartRequest request = new ModifyCartRequest();
    request.setItemId(1);
    request.setQuantity(5);
    request.setUsername(this.username);
    
    final ResponseEntity<Cart> response = this.cartController.addToCart(request);
    
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
  }
  
  @Test
  public void add_to_cart_not_found_item() {
    this.mockData();
    
    ModifyCartRequest request = new ModifyCartRequest();
    request.setItemId(10);
    request.setQuantity(5);
    request.setUsername(this.username);
    
    final ResponseEntity<Cart> response = this.cartController.addToCart(request);
    
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
  }
  
  @Test
  public void remove_from_cart_happy_path() {
    this.mockData();
    
    ModifyCartRequest request = new ModifyCartRequest();
    request.setItemId(this.mockItems.get(3).getId());
    request.setQuantity(2);
    request.setUsername(this.username);
    
    final ResponseEntity<Cart> response = this.cartController.removeFromCart(request);
    assertNotNull(response);
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

  }
  
  @Test
  public void remove_from_cart_not_found_username() {
    ModifyCartRequest request = new ModifyCartRequest();
    request.setItemId(1L);
    request.setQuantity(2);
    request.setUsername(this.username);
    
    final ResponseEntity<Cart> response = this.cartController.removeFromCart(request);
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
  }
  
  @Test
  public void remove_from_cart_not_found_item() {
    this.mockData();
    ModifyCartRequest request = new ModifyCartRequest();
    request.setItemId(10L);
    request.setQuantity(2);
    request.setUsername(this.username);
    
    final ResponseEntity<Cart> response = this.cartController.removeFromCart(request);
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());

  }

}
