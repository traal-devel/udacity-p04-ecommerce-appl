package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.util.TestUtils;

public class OrderControllerTest {

  
  /* member variables */
  private OrderController orderController;
  
  private OrderRepository orderRepository = mock(OrderRepository.class);
  
  private UserRepository userRepository = mock(UserRepository.class);
  
  
  /* constructors */
  public OrderControllerTest() {
    super();
  }
  
  
  /* methods */
  @Before
  public void setUp() {
    this.orderController = new OrderController();
    TestUtils.injectObject(this.orderController, "orderRepository", this.orderRepository);
    TestUtils.injectObject(this.orderController, "userRepository", this.userRepository);
  }
  
  @Test
  public void submit_happy_path() {
    String username = "johndoe";
    User user = TestUtils.createDefaultUserWithCartAndItems(username);
    
    when(this.userRepository.findByUsername(username)).thenReturn(user);
    
    final ResponseEntity<UserOrder> response = 
                            this.orderController.submit(username);
    
    assertNotNull(response);
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    assertNotNull(response.getBody());
    assertEquals(user, response.getBody().getUser());
    assertEquals(user.getCart().getItems(), response.getBody().getItems());
    assertNotNull(response.getBody().getTotal());
  }
  
  @Test
  public void submit_not_found_username() {
    String username = "johndoe";
    when(this.userRepository.findByUsername(any())).thenReturn(null);
    
    final ResponseEntity<UserOrder> response = 
                            this.orderController.submit(username);
    
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    
  }
  
  @Test
  public void get_orders_for_user_happy_path() {
    String username = "johndoe";
    User user = TestUtils.createDefaultUserWithCartAndItems(username);
    List<UserOrder> orders = TestUtils.createDefaultUserOrder(user, 5);
    
    when(this.userRepository.findByUsername(username)).thenReturn(user);
    when(this.orderRepository.findByUser(user)).thenReturn(orders);
    
    final ResponseEntity<List<UserOrder>> response = 
                            this.orderController.getOrdersForUser(username);
    assertNotNull(response);
    assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    assertNotNull(response.getBody());
    assertEquals(orders, response.getBody());
    
  }
  
  @Test
  public void get_orders_not_found_username() {
    when(this.userRepository.findByUsername(any())).thenReturn(null);
    
    final ResponseEntity<List<UserOrder>> response = 
                            this.orderController.getOrdersForUser("test");
    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    
  }

}
