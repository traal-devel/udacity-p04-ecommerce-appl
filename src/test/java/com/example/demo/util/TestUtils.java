package com.example.demo.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;

/**
 * Utility for tests which mimics the Sprint dependency injection approach.
 * 
 * @author traal-devel
 */
public class TestUtils {

  
  /* member variables */

  
  /* constructors */
  private TestUtils() {
    super();
  }
  
  /* methods */
  public static List<Item> getDefaultItemList(int size) {
    List<Item> items = new ArrayList<>();
    
    for (int i=0; i<size; i++) {
      Item item = new Item();
      item.setId((long)i);
      item.setName("ITEM_" + i);
      item.setPrice(new BigDecimal(100 * Math.random()));
      
      items.add(item);
    }
    
    return items;
  }
  
  public static List<UserOrder> createDefaultUserOrder(User user, int size) {
    List<UserOrder> orders = new ArrayList<UserOrder>();
    
    for (int i=0; i<size; i++) {
      List<Item> items = TestUtils.getDefaultItemList((int)(5 + 10 * Math.random()));
      UserOrder order = new UserOrder();
      
      order.setId((long)(i+1));
      order.setItems(items);
      order.setTotal(new BigDecimal(100 * Math.random()));
      order.setUser(user);
      orders.add(order);
    }
    
    return orders;
  }
  
  public static User createDefaultUser(String username) {
    User user = new User();
    
    user.setId(1);
    user.setUsername(username);
    user.setPassword("testpassword");
    user.setSalt("a_salt_to_generate");
    user.setCart(null); // at the moment.
    
    return user;
  }
  
  
  public static User createDefaultUserWithCart(String username) {
    User user = TestUtils.createDefaultUser(username);
    Cart cart = TestUtils.createDefaultCart(user, null);
    
    user.setCart(cart);
    
    return user;
  }
  
  public static User createDefaultUserWithCartAndItems(String username) {
    List<Item> items = TestUtils.getDefaultItemList(8);
    User user = TestUtils.createDefaultUser(username);
    Cart cart = TestUtils.createDefaultCart(user, items);
    
    user.setCart(cart);
    
    return user;
  }
  
  public static Cart createDefaultCart(User user, List<Item> items) {
    Cart cart = new Cart();
    cart.setId(1L);
    cart.setUser(user);
    cart.setItems(items);
    
    if (items != null) {
      BigDecimal totalPrice = 
          items.stream()
           .map(Item::getPrice)
           .reduce(BigDecimal.ZERO, (a,b) -> a.add(b));
      cart.setTotal(totalPrice);
    }
    
    return cart;
  }
  
  @SuppressWarnings("deprecation")
  public static void injectObject(
      Object target, 
      String fieldName,
      Object toInject
  ) {
    boolean wasPrivate = false;
    try {
      Field f = target.getClass().getDeclaredField(fieldName);
      
      if (!f.isAccessible()) {
        f.setAccessible(true);
        wasPrivate = true;
      }
      f.set(target, toInject);
      
      if (wasPrivate) {
        f.setAccessible(wasPrivate);
      }
      
    } catch (
        NoSuchFieldException | 
        IllegalArgumentException | 
        IllegalAccessException ex
    ) {
      ex.printStackTrace();
    }
  }
}
