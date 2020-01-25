package com.example.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.example.demo.controllers.CartControllerTest;
import com.example.demo.controllers.ItemControllerTest;
import com.example.demo.controllers.OrderControllerTest;
import com.example.demo.controllers.SecurityTest;
import com.example.demo.controllers.UserControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = {
  ItemControllerTest.class,
  UserControllerTest.class,
  OrderControllerTest.class,
  CartControllerTest.class,
  SecurityTest.class
})
public class TestSuite {

  /* member variables */

  /* constructors */

  /* methods */

}
