package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.util.SecurityUtil;

public class UserControllerTest {

  
  /* member variables */
  private UserController userController;
  
  private UserRepository userRepository = mock(UserRepository.class);
  
  private CartRepository cartRepository = mock(CartRepository.class);
  
  private BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);
  

  /* constructors */
  public UserControllerTest() {
    super();
  }
  
  /* methods */
  @Before
  public void setUp() {
    this.userController = new UserController();
    TestUtils.injectObject(this.userController, "userRepository", this.userRepository);
    TestUtils.injectObject(this.userController, "cartRepository", this.cartRepository);
    TestUtils.injectObject(this.userController, "passwordEncoder", this.passwordEncoder);
  }
  
  @Test
  public void create_user_happy_path() throws Exception {
    when(this.passwordEncoder.encode(any()))
        .thenReturn("thisIsHashed");
    
    CreateUserRequest r = new CreateUserRequest();
    r.setUsername("testUser");
    r.setPassword("testPassword");
    r.setConfirmPassword("testPassword");
    
    final ResponseEntity<User> response = userController.createUser(r);
    
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    
    User user = response.getBody();
    assertEquals(0, user.getId());
    assertEquals("testUser", user.getUsername());
    assertEquals("thisIsHashed", user.getPassword());
    
  }
  
  @Test
  public void find_user_by_name_happy_path() throws Exception {
    User userMock = new User();
    userMock.setId(0);
    userMock.setUsername("testUser");
    userMock.setPassword("testPassword");
    userMock.setSalt(SecurityUtil.generateRanomSalt());
    
    when(this.userRepository.findByUsername(any())).thenReturn(userMock);
    
    final ResponseEntity<User> response = userController.findByUserName("testUser");
    
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());
    
    User user = response.getBody();
    assertEquals(0, user.getId());
    assertEquals(userMock.getUsername(), user.getUsername());
    assertEquals(userMock.getPassword(), user.getPassword());
    
  }
}
