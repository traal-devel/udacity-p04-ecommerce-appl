package com.example.demo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.LoginRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class SecurityTest {

  
  /* member variables */
  @Autowired
  private MockMvc                             mvc;
  
  @Autowired
  private JacksonTester<LoginRequest>         jsonLoginRequest;
  
  @Autowired
  private JacksonTester<CreateUserRequest>    jsonCreateUserRequest;
  
  /* constructors */

  
  /* methods */
  @Test
  public void login_unauthorized() throws Exception {
    LoginRequest request = new LoginRequest();
    request.setUsername("admin");
    request.setPassword("test");
    
    mvc.perform(
          post("/login")
            .content(jsonLoginRequest.write(request).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isUnauthorized());
    
  }
  
  @Test
  public void login_authorized() throws Exception {
    String username = "janedoe";
    String password = "test1234#";
    CreateUserRequest request = new CreateUserRequest();
    request.setUsername(username);
    request.setPassword(password);
    request.setConfirmPassword(password);
    
    mvc.perform(
          post("/api/user/create")
            .content(jsonCreateUserRequest.write(request).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk());
    
    
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername(username);
    loginRequest.setPassword(password);
    
    mvc.perform(
          post("/login")
            .content(jsonLoginRequest.write(loginRequest).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk());
  }

}
