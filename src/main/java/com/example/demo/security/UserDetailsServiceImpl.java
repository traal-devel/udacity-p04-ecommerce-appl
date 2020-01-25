package com.example.demo.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;

/**
 * User details service implementation.
 * <p>
 * Takes a username and return a userdetails User instance with the user's 
 * username and hashed password.
 * </p>
 * 
 * @author traal-devel
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  
  /* member variables */
  @Autowired
  private UserRepository userRepository;
  
  
  /* constructors */
  public UserDetailsServiceImpl() {
    super();
  }

  
  /* methods */
  @Override
  public UserDetails loadUserByUsername(
      final String username
  ) throws UsernameNotFoundException {
    
    User user = this.userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    
    org.springframework.security.core.userdetails.User userDetails = 
        new org.springframework.security.core.userdetails.User(
              user.getUsername(), 
              user.getPassword(),
              Collections.emptyList()
            );
    
    return userDetails;
    
  }
}
