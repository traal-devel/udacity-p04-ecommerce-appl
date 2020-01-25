package com.example.demo.dto;

public class LoginDto {

  
  /* member variables */
  private String username;
  
  private String password;

  
  /* constructors */
  public LoginDto() {
    super();
  }

  
  /* methods */
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  

}
