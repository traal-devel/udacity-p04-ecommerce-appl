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

import com.example.demo.model.requests.LoginRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class SareetaApplicationTest {

  
  /* member variables */
  @Autowired
  private MockMvc                       mvc;
  
  @Autowired
  private JacksonTester<LoginRequest>   jsonReview;
  
  /* constructors */

  
  /* methods */
  @Test
  public void login() throws Exception {
    LoginRequest request = new LoginRequest();
    request.setUsername("admin");
    request.setPassword("test");
    
    mvc.perform(
          post("/login")
            .content(jsonReview.write(request).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk());
    
  }

}
