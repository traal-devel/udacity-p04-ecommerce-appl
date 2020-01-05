package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO CreateUserRequest
 * 
 * @author traal-devel
 */
public class CreateUserRequest {

  
  /* member variables */
	@JsonProperty
	private String username;
	
	@JsonProperty
	private String password;
	
	@JsonProperty
	private String confirmPassword;
	

	/* constructors */
	public CreateUserRequest() {
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

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
	
	
}
